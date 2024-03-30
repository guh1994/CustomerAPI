package com.customer.api;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(
    @NotBlank String name,
    @NotBlank String email )
    implements
        Customer
{

    public static CustomerDTO convert(
        final Customer customer )
    {
        return new CustomerDTO( customer.name(), customer.email() );
    }

    public static List<CustomerDTO> convert(
        final List<Customer> customers )
    {
        return customers.stream().map( CustomerDTO::convert ).toList();
    }
}
