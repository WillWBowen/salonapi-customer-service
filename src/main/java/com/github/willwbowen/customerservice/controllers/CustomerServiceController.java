package com.github.willwbowen.customerservice.controllers;

import com.github.willwbowen.customerservice.config.ServiceConfig;
import com.github.willwbowen.customerservice.models.Customer;
import com.github.willwbowen.customerservice.services.CustomerService;
import com.github.willwbowen.customerservice.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/v1/salons/{salonId}/customers")
public class CustomerServiceController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ServiceConfig serviceConfig;

    @GetMapping()
    public List<Customer> getCustomers(@PathVariable("salonId") String salonId) {
        logger.debug("CustomerServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return customerService.getCustomersBySalon(salonId);
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable("salonId") String salonId, @PathVariable("customerId") String customerId) {
        return customerService.getCustomer(salonId,customerId);
    }

    @PutMapping("/{customerId}")
    public void updateCustomer(@PathVariable("customerId") String customerId, @RequestBody Customer customer) {
        customerService.updateCustomer(customer);
    }

    @PostMapping()
    public void saveCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("customerId") String customerId, @RequestBody Customer customer) {
        customerService.deleteCustomer(customer);
    }
}
