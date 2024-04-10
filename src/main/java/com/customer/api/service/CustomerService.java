package com.customer.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.api.model.PersistentCustomer;
import com.customer.api.repository.CustomerRepository;
import com.customer.api.rest.RestCustomer;
import com.customer.api.validator.RestEntityResponse;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public List<RestCustomer> getCustomers() {
        final List<PersistentCustomer> allCustomers = repository.findAll();
        return RestCustomer.convert(allCustomers);
    }

    public RestEntityResponse<RestCustomer> getCustomerById(
            final Integer id) {

        final PersistentCustomer persistentCustomer = repository.findCustomerById(id);
        if (persistentCustomer != null) {
            RestCustomer restCustomer = RestCustomer.convert(persistentCustomer);
            return RestEntityResponse.createSuccess(restCustomer);
        }
        return RestEntityResponse.createError(List.of("Customer not found"));
    }

    public RestEntityResponse<RestCustomer> createCustomer(
            final RestCustomer customer) {

        final List<String> messages = validateCommons(customer);
        if (!messages.isEmpty()) {
            return RestEntityResponse.createError(messages);
        }

        final boolean existsByEmail = repository.existsByEmail(customer.email());
        if (existsByEmail) {
            messages.add("Customer allready exist with this email");
            return RestEntityResponse.createError(messages);
        }

        final PersistentCustomer persistedCustomer = repository.save(PersistentCustomer.create(customer));
        return RestEntityResponse.createSuccess(RestCustomer.convert(persistedCustomer));

    }

    private List<String> validateCommons(
            final RestCustomer customer) {
        if (customer == null) {
            return List.of("O Payload está nulo.");
        }
        final List<String> messages = new ArrayList<>();
        if (Strings.isEmpty(customer.email())) {
            messages.add("O email está inválido");
        }
        if (Strings.isEmpty(customer.name())) {
            messages.add("O nome está inválido");
        }
        return messages;
    }

    public RestEntityResponse<RestCustomer> updateCustomer(
            final RestCustomer customer,
            final Integer id) {

        final List<String> messages = validateCommons(customer);
        if (!messages.isEmpty()) {
            return RestEntityResponse.createError(messages);
        }

        final Optional<PersistentCustomer> persistedCustomer = repository.findById(id);
        if (persistedCustomer.isEmpty()) {
            messages.add("Customer with id %s not exists".formatted(id));
            return RestEntityResponse.createError(messages);
        }

        final PersistentCustomer updatedCustomer = persistedCustomer.get();
        updatedCustomer.update(customer);

        return RestEntityResponse.createSuccess(RestCustomer.convert(updatedCustomer));

    }

    public RestEntityResponse<RestCustomer> deleteCustomer(
            final Integer id) {
        if (id == null) {
            return RestEntityResponse.createError(List.of("Customer deletion id is null."));
        }

        repository.deleteById(id);
        return RestEntityResponse.createSuccess("Customer Deleted with success");
    }


    public void throwExceptionForTest() {
        throw new RuntimeException("Test...");
    }

}
