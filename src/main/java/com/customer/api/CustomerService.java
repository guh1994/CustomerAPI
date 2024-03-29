package com.customer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public List<CustomerPersistent> getCustomers() {

        return repository.findAll();

    }

    public Optional<CustomerPersistent> getCustomerById(Integer id) {

        return repository.findById(id);

    }

    public void createCustomer(CustomerPersistent customer) {

        repository.saveAndFlush(customer);

    }

    public Optional<CustomerPersistent> updateCustomer(CustomerPersistent customer, Integer id) {

        repository.saveAndFlush(customer);

        Optional<CustomerPersistent> customerUpdated = repository.findById(id);

        return customerUpdated;

    }

    public void deleteCustomer(Integer id) {

        repository.deleteById(id);
    }

}
