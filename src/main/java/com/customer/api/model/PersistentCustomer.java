package com.customer.api.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customer")
public class PersistentCustomer
        implements
        Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;

    // Hibernate
    public PersistentCustomer() {

    }

    public PersistentCustomer(
            final Integer id,
            final String name,
            final String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static PersistentCustomer convert(
            final Customer customer) {
        return new PersistentCustomer(customer.id(), customer.name(), customer.email());
    }

    public static List<PersistentCustomer> convert(
            final List<Customer> customers) {
        return customers.stream().map(PersistentCustomer::convert).toList();
    }

    public void update(Customer customer) {
        this.id = customer.id();
        this.name = customer.name();
        this.email = customer.email();
    }

    @Override
    public Integer id() {
        return id;
    }
    @Override
    public String name() {
        return name;
    }

    @Override
    public String email() {
        return email;
    }


}
