package com.github.willwbowen.customerservice.services;

import com.github.willwbowen.customerservice.clients.SalonRestTemplateClient;
import com.github.willwbowen.customerservice.config.ServiceConfig;
import com.github.willwbowen.customerservice.models.Customer;
import com.github.willwbowen.customerservice.models.Salon;
import com.github.willwbowen.customerservice.repository.CustomerRepository;
import com.github.willwbowen.customerservice.utils.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    ServiceConfig config;

    @Autowired
    SalonRestTemplateClient salonRestClient;

    @HystrixCommand
    private Salon retrieveSalonInfo(String salonId) {
        return salonRestClient.getSalon(salonId);
    }

    public Customer getCustomer(String salonId, String customerId) {
        Customer customer = customerRepository.findBySalonIdAndCustomerId(salonId, customerId);
        Salon salon = retrieveSalonInfo(salonId);

        return customer.withSalonName(salon.getName());
    }

    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(
                            name="execution.isolation.thread.timeoutInMilliseconds",
                            value="3000"
                    )
            },
            fallbackMethod = "buildFallbackCustomerList",
            threadPoolKey = "customersBySalonPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value="30"),
                    @HystrixProperty(name="maxQueueSize", value="10")
            }
    )
    public List<Customer> getCustomersBySalon(String salonId) {
        logger.debug("CustomerService.getCustomersBySalon Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        randomlyRunLong();
        return customerRepository.findBySalonId(salonId);
    }

    public void saveCustomer(Customer customer) {
        customer.withId(UUID.randomUUID().toString());

        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    private void randomlyRunLong() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

        if(randomNum == 3) sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<Customer> buildFallbackCustomerList(String salonId) {
        List<Customer> fallbackList = new ArrayList<>();
        Customer customer = new Customer()
                .withId("00000000000")
                .withSalonName("Sorry, no information currently available");
        fallbackList.add(customer);
        return fallbackList;
    }

}
