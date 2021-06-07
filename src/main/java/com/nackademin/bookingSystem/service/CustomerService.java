package com.nackademin.bookingSystem.service;


import com.nackademin.bookingSystem.model.Customer;
import com.nackademin.bookingSystem.repository.CustomerRepo;
import com.nackademin.bookingSystem.utils.utils.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ashkan Amiri
 * Date:  2021-06-07
 * Time:  12:23
 * Project: BookingSystem
 * Copyright: MIT
 */
@Service
public class CustomerService {

    @Autowired
    CustomerRepo repository;

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Optional<Customer> getUserById(Long id) {
        return repository.findById(id);
    }

    public Customer addCustomer(Customer customer) throws UserException {
        Customer c = repository.findByEmail(customer.getEmail());
        if (c != null) {
            throw new UserException("This E-mail already exist");
        } else {
            return repository.save(customer);
        }
    }
}
