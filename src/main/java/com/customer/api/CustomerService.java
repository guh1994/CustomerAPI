package com.customer.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService
{

    @Autowired
    private CustomerRepository repository;

    public List<PersistentCustomer> getCustomers()
    {

        return repository.findAll();

    }

    public Optional<PersistentCustomer> getCustomerById(
        final Integer id )
    {

        return repository.findById( id );

    }

    public void createCustomer(
        final CustomerDTO customer )
    {

        final PersistentCustomer customerCreate = PersistentCustomer.convert( customer );

        repository.saveAndFlush( customerCreate );

    }

    public Optional<PersistentCustomer> updateCustomer(
        final PersistentCustomer customer,
        final Integer id )
    {

        repository.saveAndFlush( customer );

        final Optional<PersistentCustomer> customerUpdated = repository.findById( id );

        return customerUpdated;

    }

    public void deleteCustomer(
        final Integer id )
    {

        repository.deleteById( id );
    }

}
