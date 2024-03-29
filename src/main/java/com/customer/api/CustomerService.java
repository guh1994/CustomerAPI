package com.customer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public List<Customer> getCustomers() {

        return repository.findAll();

    }

    public Optional<Customer> getCustomerById(Integer id) {

        return repository.findById(id);

    }


}
