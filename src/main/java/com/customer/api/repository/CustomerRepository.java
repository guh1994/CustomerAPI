package com.customer.api.repository;

import com.customer.api.model.PersistentCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository
        extends
        JpaRepository<PersistentCustomer, Integer> {

    public boolean existsByEmail(String email);

    public PersistentCustomer findCustomerById(Integer id);

    public PersistentCustomer findCustomerByEmail(String email);
}
