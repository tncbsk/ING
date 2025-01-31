package com.ing.creditModule.service;

import com.ing.creditModule.entity.Customer;
import com.ing.creditModule.exception.CustomerException;
import com.ing.creditModule.repos.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer mockCustomer;

    @BeforeEach
    public void setUp() {

        mockCustomer = new Customer();
        mockCustomer.setId(1L);
        mockCustomer.setName("Steve");
        mockCustomer.setSurname("Nash");
    }

    @Test
    void testFindByCustomerId_Success() {

        when(customerRepository.findById(1L)).thenReturn(Optional.of(mockCustomer));

        Customer customer = customerService.findByCustomerId(1L);
        assertNotNull(customer, "Customer should not be null");
        assertEquals(1L, customer.getId(), "");
        assertEquals("Steve", customer.getName(), "");

        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByCustomerId_NotFound() {

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.findByCustomerId(1L));

        assertEquals("Customer not found with id: 1", exception.getMessage(), "Exception message is same");

        verify(customerRepository, times(1)).findById(1L);
    }


    @Test
    void saveOrUpdateCustomer() {

        customerService.saveOrUpdateCustomer(mockCustomer);
        verify(customerRepository, times(1)).save(argThat(argument ->
                argument.getId().equals(mockCustomer.getId()) &&
                        argument.getName() == mockCustomer.getName()));

    }

    @Test
    void testSaveOrUpdateCustomer_Failure() {

        doThrow(new RuntimeException("Database error")).when(customerRepository).save(mockCustomer);

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.saveOrUpdateCustomer(mockCustomer));

        assertTrue(exception.getMessage().contains("Error Save/Update customer, transaction rolled back"));
    }
}