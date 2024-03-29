package com.customer.api;

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
    public ResponseEntity<List<Customer>> getCustomers() {

        List<Customer> customers = service.getCustomers();

        return new ResponseEntity<>(customers,HttpStatus.OK);

    }

    @GetMapping(value = "/customer/{id}")
    public void getCustomerById(@PathVariable Integer id) {

    }

    @PostMapping(value = "/customer/create")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {

        return new ResponseEntity<>("Created Success",HttpStatus.CREATED);
    }

    @PutMapping(value = "/customer/update/{id}")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) {

        return ResponseEntity.ok("Updated Success");
    }

    @DeleteMapping(value = "/customer/delete/{id}")
    public ResponseEntity<String> deleteCustomer() {
        return ResponseEntity.ok("Deleted Success");
    }


}
