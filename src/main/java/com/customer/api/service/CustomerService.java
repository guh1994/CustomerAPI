package com.customer.api.service;

import com.customer.api.rest.RestCustomer;
import com.customer.api.validator.RestEntityResponse;

import java.util.List;

public interface CustomerService {

    List<RestCustomer> getCustomers();

    RestEntityResponse<RestCustomer> getCustomerByEmail(final String email);

    RestEntityResponse<RestCustomer> createCustomer(final RestCustomer customer);

    RestEntityResponse<RestCustomer> updateCustomer(final RestCustomer customer, final String email);

    RestEntityResponse<RestCustomer> deleteCustomer(final Integer id);

}
