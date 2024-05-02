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

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public List<RestCustomer> getCustomers() {
        final List<PersistentCustomer> allCustomers = repository.findAll();
        return RestCustomer.convert(allCustomers);
    }

    public RestEntityResponse<RestCustomer> getCustomerByEmail(
            final String email) {

        final PersistentCustomer persistentCustomer = repository.findCustomerByEmail(email);
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
            return RestEntityResponse.createError(List.of("Customer allready exist with this email"));
        }

        final PersistentCustomer persistedCustomer = repository.save(PersistentCustomer.create(customer));
        return RestEntityResponse.createSuccess(RestCustomer.convert(persistedCustomer));

    }


    public RestEntityResponse<RestCustomer> updateCustomer(
            final RestCustomer customer,
            final String email) {

        final List<String> messages = validateCommons(customer);
        if (!messages.isEmpty()) {
            return RestEntityResponse.createError(messages);
        }

        final PersistentCustomer persistedCustomer = repository.findCustomerByEmail(email);
        if (persistedCustomer == null) {
            messages.add("Customer with email %s not exists".formatted(email));
            return RestEntityResponse.createError(messages);
        }

        final PersistentCustomer updatedCustomer = persistedCustomer;
        updatedCustomer.update(customer);

        PersistentCustomer customerUpdated = repository.save(updatedCustomer);

        return RestEntityResponse.createSuccess(RestCustomer.convert(customerUpdated));

    }

    public RestEntityResponse<RestCustomer> deleteCustomer(
            final Integer id) {
        if (id == null) {
            return RestEntityResponse.createError(List.of("Customer deletion id is null."));
        }

        repository.deleteById(id);
        return RestEntityResponse.createSuccess("Customer Deleted with success");
    }

    private List<String> validateCommons(
            final RestCustomer customer) {
        if (customer == null) {
            return List.of("O Payload está nulo.");
        }
        final List<String> messages = new ArrayList<>();
        if (Strings.isEmpty(customer.email())) {
            messages.add("O email está vazio");
        }
        if (Strings.isEmpty(customer.name())) {
            messages.add("O nome está vazio");
        }
        if (!customer.email().matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            messages.add("O email está inválido");
        }
        if (!customer.name().matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\\\s-]{3,}$")) {
            messages.add("O nome está inválido");
        }

        return messages;
    }

    public void throwExceptionForTest() {
        throw new RuntimeException("Test...");
    }

    public void validateName(String name) {


    }

}
