package com.customer.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.customer.api.rest.RestCustomer;
import com.customer.api.service.CustomerService;
import com.customer.api.validator.RestEntityResponse;

@Controller
public class CustomerController
{

    @Autowired
    private CustomerService service;

    @GetMapping( value = "/customers" )
    public ResponseEntity<RestEntityResponse<List<RestCustomer>>> getCustomers()
    {

        final List<RestCustomer> customers = service.getCustomers();
        if( customers.isEmpty() ) {
            final List<String> errorMessages = List.of( "Não há clientes cadastrados" );
            return ResponseEntity.ok( new RestEntityResponse<List<RestCustomer>>( false, errorMessages, List.of() ) );
        }
        return ResponseEntity.ok( RestEntityResponse.createSuccess( customers ) );

    }

    @GetMapping( value = "/customer/{id}" )
    public ResponseEntity<RestCustomer> getCustomerById(
        @PathVariable final Integer id )
    {
        final RestCustomer customer = service.getCustomerById( id );

        return new ResponseEntity<>( customer, HttpStatus.OK );

    }

    @PostMapping( value = "/customer/create" )
    public ResponseEntity<RestEntityResponse<RestCustomer>> createCustomer(
        @RequestBody final RestCustomer customer )
    {
        final RestEntityResponse<RestCustomer> responseEntity = service.createCustomer( customer );
        if( ! responseEntity.success() ) {
            return ResponseEntity.badRequest().body( responseEntity );
        }

        return ResponseEntity.ok( responseEntity );
    }

    @PutMapping( value = "/customer/update/{id}" )
    public ResponseEntity<RestCustomer> updateCustomer(
        @PathVariable final Integer id,
        @RequestBody final RestCustomer customer )
    {

        final RestCustomer customerUpdated = service.updateCustomer( customer, id );

        return new ResponseEntity<>( customerUpdated, HttpStatus.OK );
    }

    @DeleteMapping( value = "/customer/delete/{id}" )
    public ResponseEntity<String> deleteCustomer(
        @PathVariable final Integer id )
    {

        service.deleteCustomer( id );

        return new ResponseEntity<>( "Deleted", HttpStatus.OK );
    }

}
