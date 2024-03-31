package com.customer.api.rest;

import com.customer.api.model.Customer;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record RestCustomer(
    @NotBlank String name,
    @NotBlank String email )
    implements
        Customer
{

    public static RestCustomer convert(
        final Customer customer )
    {
        return new RestCustomer(customer.name(), customer.email());
    }

    public static List<RestCustomer> convert(
        final List<Customer> customers )
    {
        return customers.stream().map(RestCustomer::convert).toList();
    }
}
