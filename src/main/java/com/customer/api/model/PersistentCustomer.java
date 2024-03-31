package com.customer.api.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table( name = "customer" )
public class PersistentCustomer
    implements
        Customer
{

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id" )
    private Integer id;

    @Column( name = "name" )
    private String name;
    @Column( name = "email" )
    private String email;

    // Hibernate
    public PersistentCustomer()
    {

    }

    public static PersistentCustomer convert(
        final Customer customer )
    {
        return new PersistentCustomer( customer.name(), customer.email() );
    }

    public static List<PersistentCustomer> convert(
        final List<Customer> customers )
    {
        return customers.stream().map( PersistentCustomer::convert ).toList();
    }

    public PersistentCustomer(
        final String name,
        final String email )
    {
        this.name = name;
        this.email = email;
    }

    @Override
    public String name()
    {
        return name;
    }

    @Override
    public String email()
    {
        return email;
    }

}
