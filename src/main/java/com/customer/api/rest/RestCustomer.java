package com.customer.api.rest;

import java.util.List;

import com.customer.api.model.Customer;

import jakarta.validation.constraints.NotBlank;

public record RestCustomer(
        @NotBlank String name,
        @NotBlank String email)
        implements
        Customer {

    public static RestCustomer convert(
            final Customer customer) {
        return new RestCustomer(customer.name(), customer.email());
    }

    public static List<RestCustomer> convert(
            final List<? extends Customer> customers) {
        return customers.stream().map(RestCustomer::convert).toList();
    }

}
