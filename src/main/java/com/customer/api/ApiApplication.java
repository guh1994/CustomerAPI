package com.customer.api;

import com.customer.api.rest.RestCustomer;
import com.customer.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class ApiApplication {

    @Autowired
    private CustomerService customerService;


    public static void main(
            final String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    @Profile("!test")
    CommandLineRunner init() {
        return createDefaultUser("prod@gmail.com", "Prod");
    }

    @Bean
    @Profile("test")
    CommandLineRunner initOnTest() {
        return createDefaultUser("teste@gmail.com", "Test");
    }

    private CommandLineRunner createDefaultUser(
            final String email,
            final String name) {
        return args -> {
            customerService.createCustomer(new RestCustomer(name, email));
        };
    }
}
