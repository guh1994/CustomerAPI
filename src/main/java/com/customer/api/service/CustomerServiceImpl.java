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
public class CustomerServiceImpl implements CustomerService {

    public static final String EMAIL_VALIDATOR_REGEX = "^[\\w.-]+@[\\w.-]+\\.\\w+$";
    public static final String NAME_MIN_SIZE_3_REGEX = "^[a-zA-ZÀ-ú\\s]{3,}$";

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

        persistedCustomer.update(customer);

        PersistentCustomer customerUpdated = repository.save(persistedCustomer);

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
        if (!messages.isEmpty()) {
            return messages;
        }
        if (!customer.email().matches(EMAIL_VALIDATOR_REGEX)) {
            messages.add("O email está inválido");
        }
        if (!customer.name().matches(NAME_MIN_SIZE_3_REGEX)) {
            messages.add("O nome está inválido");
        }
        return messages;

    }

    void throwExceptionForTest() {
        throw new RuntimeException("Test...");
    }

}
