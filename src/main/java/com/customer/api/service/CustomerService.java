package com.customer.api.service;

import com.customer.api.model.PersistentCustomer;
import com.customer.api.repository.CustomerRepository;
import com.customer.api.rest.RestCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    /**
     * Method to get all customer with converted
     *
     * @return List<RestCustomer>
     */
    public List<RestCustomer> getCustomers() {

        //Getting all customer on DB
        List<PersistentCustomer> allCustomers = repository.findAll();

        //Create a list to add customer converted
        List<RestCustomer> restCustomers = new ArrayList<>();

        //foreach to add customer in list converted
        for (PersistentCustomer persistentCustomer : allCustomers) {

            //adding customer converted on the list
            restCustomers.add(RestCustomer.convert(persistentCustomer));
        }

        return restCustomers;
    }

    /**
     * Find customer by id
     *
     * @param id
     * @return RestCustomer
     */
    public RestCustomer getCustomerById(final Integer id) {

        //Getting customer by id
        Optional<PersistentCustomer> responseRepository = repository.findById(id);

        //create a object type RestCustomer
        RestCustomer response = null;

        //Validation response of repository to convert my customer in a RestCustomer
        if (responseRepository.isPresent()) {

            //Converting ind a RestCustomer
            response = RestCustomer.convert(responseRepository.get());
        }
        return response;

    }

    public void createCustomer(final RestCustomer customer) {

        final PersistentCustomer customerCreate = PersistentCustomer.convert(customer);

        repository.saveAndFlush(customerCreate);

    }

    public RestCustomer updateCustomer(
            final RestCustomer customer,
            final Integer id) {

        PersistentCustomer updateCustomer = PersistentCustomer.convert(customer);

        repository.saveAndFlush(updateCustomer);

        Optional<PersistentCustomer> responseRepository = repository.findById(id);

        RestCustomer response = null;
        if (responseRepository.isPresent()) {
            response = RestCustomer.convert(responseRepository.get());
        }
        return RestCustomer.convert(response);

    }

    public void deleteCustomer(
            final Integer id) {

        repository.deleteById(id);
    }

}
