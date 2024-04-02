package com.customer.api.controller;

import com.customer.api.rest.RestCustomer;
import com.customer.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping(value = "/customers")
    public ResponseEntity<List<RestCustomer>> getCustomers() {

        List<RestCustomer> customers = service.getCustomers();

        return new ResponseEntity<>(customers,HttpStatus.OK);

    }

    @GetMapping(value = "/customer/{id}")
    public ResponseEntity<RestCustomer> getCustomerById(@PathVariable Integer id) {

        RestCustomer customer = service.getCustomerById(id);

        return new ResponseEntity<>(customer, HttpStatus.OK);

    }

    @PostMapping(value = "/customer/create")
    public ResponseEntity<String> createCustomer(@RequestBody RestCustomer customer) {

        service.createCustomer(customer);

        return new ResponseEntity<>("Created Success",HttpStatus.CREATED);
    }

    @PutMapping(value = "/customer/update/{id}")
    public ResponseEntity<RestCustomer> updateCustomer(@PathVariable Integer id,
                                                       @RequestBody RestCustomer customer) {

        RestCustomer customerUpdated = service.updateCustomer(customer, id);

        return new ResponseEntity<>(customerUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/customer/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer id) {

        service.deleteCustomer(id);

        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }


}
