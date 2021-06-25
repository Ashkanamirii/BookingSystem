package com.nackademin.bookingSystem.service;


import com.nackademin.bookingSystem.model.Customer;
import com.nackademin.bookingSystem.model.RolesCustomer;
import com.nackademin.bookingSystem.repository.CustomerRepo;
import com.nackademin.bookingSystem.repository.RolesRepo;
import com.nackademin.bookingSystem.utils.utils.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    RolesRepo rolesRepository;

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
    public boolean emailExists(String email){
        return repository.existsByEmail(email);
    }

    public Customer getCustomerByEmail(String email){
        return repository.findByEmail(email);
    }
    public Customer addCustomerAsUser(Customer customer){
        return repository.save(insertRole(customer,"ROLE_USER"));
    }
    public Customer updateCustomer(Customer customer){return repository.save(customer);}

    public Customer addCustomerAsAdmin(Customer customer){
        return repository.save(insertRole(customer,"ROLE_ADMIN"));
    }


    private Customer insertRole(Customer customer, String role){
        RolesCustomer roleType=rolesRepository.findByRoleType(role);
        Set<RolesCustomer> customersRoles=customer.getRoles();
        customersRoles.add(roleType);
        customer.setRoles(customersRoles);
        return customer;
    }
}
