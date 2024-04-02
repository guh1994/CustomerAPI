package com.customer.api.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    private PersistentCustomer(
        final Integer id,
        final String name,
        final String email )
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static PersistentCustomer create(
        final Customer customer )
    {
        return new PersistentCustomer( null, customer.name(), customer.email() );
    }

    public static List<PersistentCustomer> create(
        final List<? extends Customer> customers )
    {
        return customers.stream().map( PersistentCustomer::create ).toList();
    }

    public void update(
        final Customer customer )
    {
        this.name = customer.name();
        this.email = customer.email();
    }

    public Integer id()
    {
        return id;
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
