package com.ing.creditModule.service;

import com.ing.creditModule.entity.Customer;
import com.ing.creditModule.exception.CustomerException;
import com.ing.creditModule.repos.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {


    private final CustomerRepository customerRepository;

    /**
     * This method finds Customer  If exists
     * Otherwise @exception CustomerException Customer not found with id
     *
     * @param customerId
     * @return
     * @throws CustomerException If there a error during the transaction , method raise  "Customer not found with id"
     */
    public Customer findByCustomerId(Long customerId) {

        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerException("Customer not found with id: " + customerId));
    }

    /**
     * This method Saves a new customer or updates an existing customer in the database.
     *
     * @param customer
     * @throws CustomerException If there a error during the transaction , method raise  "Error Save/Update customer, transaction rolled back"
     */
    public void saveOrUpdateCustomer(Customer customer) {
        try {
            customerRepository.save(customer);
        } catch (Exception ex) {
            throw new CustomerException("Error Save/Update customer, transaction rolled back." + ex.getMessage());
        }

    }
}
