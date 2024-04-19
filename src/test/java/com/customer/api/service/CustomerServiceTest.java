package com.customer.api.service;

import com.customer.api.model.PersistentCustomer;
import com.customer.api.repository.CustomerRepository;
import com.customer.api.rest.RestCustomer;
import com.customer.api.validator.RestEntityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerServiceTest {

    public static final String NAME = "Gustavo";
    public static final String EMAIL = "gustavo@gmail.com";
    public static final int CUSTOMER_ID = 1;
    @InjectMocks
    private CustomerService subject;

    @Mock
    private CustomerRepository repository;

    @Mock
    private PersistentCustomer persistentCustomer;

    @BeforeEach
    public void setup() {
        when(persistentCustomer.name()).thenReturn(NAME);
        when(persistentCustomer.email()).thenReturn(EMAIL);
        when(repository.findAll()).thenReturn(List.of(persistentCustomer));
        when(repository.findCustomerById(CUSTOMER_ID)).thenReturn(persistentCustomer);
        when(repository.save(any())).thenReturn(persistentCustomer);

    }

    @Test
    public void shouldGetCustomers() {

        List<RestCustomer> customers = subject.getCustomers();

        assertEquals(1, customers.size());
        RestCustomer restCustomer = customers.get(0);

        assertEquals(NAME, restCustomer.name());
        assertEquals(EMAIL, restCustomer.email());
    }

    @Test
    public void shouldGetEmptyCustomers() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<RestCustomer> customers = subject.getCustomers();
        assertEquals(0, customers.size());
    }

    @Test
    public void shouldGetCustomerById() {
        RestEntityResponse<RestCustomer> restEntityResponse = subject.getCustomerById(1);

        RestCustomer restCustomer = restEntityResponse.entity();

        assertEquals(NAME, restCustomer.name());
        assertEquals(EMAIL, restCustomer.email());
    }


    @Test
    public void shouldGetErrorMessageWhenCustomerNotFound() {

        RestEntityResponse<RestCustomer> restEntityResponse = subject.getCustomerById(12346);

        assertNull(restEntityResponse.entity());
        assertEquals(List.of("Customer not found"), restEntityResponse.messages());
    }

    @Test
    public void shouldCreateCustomer() {

        RestCustomer customer = new RestCustomer(persistentCustomer.name(), persistentCustomer.email());

        RestEntityResponse<RestCustomer> restEntityResponse = subject.createCustomer(customer);

        assertNotNull(restEntityResponse.entity());
        assertEquals(RestEntityResponse.createSuccess(customer), restEntityResponse);

    }

    @Test
    @Disabled("TODO")
    public void shouldValidateIfEmailCustomerExistsBeforeCreate() {

        RestEntityResponse<RestCustomer> restEntityResponse = mock(RestEntityResponse.class);

        RestCustomer restCustomer = new RestCustomer(persistentCustomer.name(), persistentCustomer.email());

        assertEquals(List.of("Customer allready exist with this email"), verify(subject.createCustomer(restCustomer), times(2)).messages());

    }

    @Test
    @Disabled("TODO")
    public void shouldReturnErrorMessageWhenCustomerNameIsNullI() {

    }

    @Test
    @Disabled("TODO")
    public void ShouldReturnErrorMessageWhenCustomerEmailIsNull() {

    }

    @Test
    @Disabled("TODO")
    public void shouldReturnErrorMessageWhenCustomerIsNull() {

    }


    @Test
    public void shouldDeleteCustomer() {
        RestEntityResponse<RestCustomer> restEntityResponse = subject.deleteCustomer(CUSTOMER_ID);

        assertNull(restEntityResponse.entity());
        assertEquals(List.of("Customer Deleted with success"), restEntityResponse.messages());
        Mockito.verify(repository).deleteById(CUSTOMER_ID);

    }

    @Test
    public void shouldReturnErrorMessagesWhenCustomerIdIsNullInDeletion() {
        RestEntityResponse<RestCustomer> restEntityResponse = subject.deleteCustomer(null);

        assertEquals(List.of("Customer deletion id is null."), restEntityResponse.messages());
        Mockito.verify(repository, never()).deleteById(any());
    }


    @Test
    public void shouldThrowException() {

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            subject.throwExceptionForTest();
        });

        assertTrue(runtimeException.getMessage().contains("Test..."));

    }


}
