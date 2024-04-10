package com.customer.api.controller;

import com.customer.api.rest.RestCustomer;
import com.customer.api.service.CustomerService;
import com.customer.api.validator.RestEntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<RestEntityResponse<RestCustomer>> getCustomerById(
        @PathVariable final Integer id )
    {
        final RestCustomer customer = service.getCustomerById( id );

        if (customer == null) {

            final List<String> errorMessages = List.of("Não há cliente cadastrado com esse id");
            return ResponseEntity.ok(new RestEntityResponse<RestCustomer>(false, errorMessages, null));

        }

        return ResponseEntity.ok(RestEntityResponse.createSuccess(customer));

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
    public ResponseEntity<RestEntityResponse<RestCustomer>> updateCustomer(
        @PathVariable final Integer id,
        @RequestBody final RestCustomer customer )
    {

        final RestEntityResponse<RestCustomer> responseEntity = service.updateCustomer(customer, id);
        if (!responseEntity.success()) {
            return ResponseEntity.badRequest().body(responseEntity);
        }

        return ResponseEntity.ok(responseEntity);
    }

    @DeleteMapping( value = "/customer/delete/{id}" )
    public ResponseEntity<String> deleteCustomer(
        @PathVariable final Integer id )
    {

        final RestEntityResponse<RestCustomer> responseEntity = service.deleteCustomer(id);

        if (!responseEntity.success()) {
            return ResponseEntity.badRequest().body("Não foi possivel deletar o cliente");
        }

        return new ResponseEntity<>( "Deleted", HttpStatus.OK );
    }

}
