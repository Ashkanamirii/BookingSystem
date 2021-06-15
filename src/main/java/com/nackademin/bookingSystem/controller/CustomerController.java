package com.nackademin.bookingSystem.controller;

import com.nackademin.bookingSystem.model.Customer;
import com.nackademin.bookingSystem.service.CustomerService;
import com.nackademin.bookingSystem.utils.utils.Encrypt;
import com.nackademin.bookingSystem.utils.utils.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ashkan Amiri
 * Date:  2021-06-07
 * Time:  12:24
 * Project: BookingSystem
 * Copyright: MIT
 */
@RequestMapping("/customer")
@RestController
public class CustomerController {

    @Autowired
    CustomerService service;

    //this request is to test roles are working properly
    @Secured("ROLE_ADMIN")
    @GetMapping("foradmin")
    public String foradmin(){
        return "this is only for admin";
    }
    @GetMapping("/get")
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @GetMapping("/get/{id}")
    public Optional<Customer> getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }


    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@RequestBody Customer customer) {
        Customer c = null;
        try {
            customer.setPassword(Encrypt.getMd5(customer.getPassword()));
            c = service.addCustomer(customer);
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(c);
    }

}
