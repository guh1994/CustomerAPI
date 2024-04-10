package com.customer.api.service;

import com.customer.api.model.PersistentCustomer;
import com.customer.api.repository.CustomerRepository;
import com.customer.api.rest.RestCustomer;
import com.customer.api.validator.RestEntityResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public List<RestCustomer> getCustomers() {

        final List<PersistentCustomer> allCustomers = repository.findAll();

        return RestCustomer.convert(allCustomers);
    }

    public RestCustomer getCustomerById(
            final Integer id) {

        final Optional<PersistentCustomer> persistentCustomer = repository.findById(id);
        if (persistentCustomer.isPresent()) {
            return RestCustomer.convert(persistentCustomer.get());
        }

        return null;
    }

    public RestEntityResponse<RestCustomer> createCustomer(
            final RestCustomer customer) {

        final List<String> messages = validateCommons(customer);
        if (!messages.isEmpty()) {
            return RestEntityResponse.createError(messages);
        }

        // TODO Validate in database existsByField

        boolean existsByEmail = repository.existsByEmail(customer.email());
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
        if (!persistedCustomer.isPresent()) {
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
            List<String> messages = new ArrayList<>();
            messages.add("Customer deletion id is null.");

            return RestEntityResponse.deleteError(messages);
        }

        repository.deleteById(id);

        return RestEntityResponse.deleteSuccess();
    }

}
