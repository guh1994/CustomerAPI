package com.customer.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.api.model.PersistentCustomer;

@Repository
public interface CustomerRepository
    extends
        JpaRepository<PersistentCustomer,Integer>
{
}
