package com.github.willwbowen.customerservice.repository;

import com.github.willwbowen.customerservice.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {
    public List<Customer> findBySalonId(String salonId);
    public Customer findBySalonIdAndCustomerId(String salonId, String customerId);
}
