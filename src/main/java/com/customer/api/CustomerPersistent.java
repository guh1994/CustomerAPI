package com.customer.api;

import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class CustomerPersistent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;

    public CustomerPersistent(CustomerDTO customer) {
        this.name = customer.name();
        this.email = customer.email();

    }
}
