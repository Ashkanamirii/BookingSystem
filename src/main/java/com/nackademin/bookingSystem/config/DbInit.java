package com.nackademin.bookingSystem.config;

import com.nackademin.bookingSystem.model.Customer;
import com.nackademin.bookingSystem.model.RolesCustomer;
import com.nackademin.bookingSystem.repository.CustomerRepo;
import com.nackademin.bookingSystem.repository.RolesRepo;
import com.nackademin.bookingSystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Set;

/**
 * Created by Hodei Eceiza
 * Date: 6/16/2021
 * Time: 23:55
 * Project: BookingSystem
 * Copyright: MIT
 */
@Component
public class DbInit {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private RolesRepo rolesRepo;


    @PostConstruct
    private void postConstruct(){

//create an initial admin
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        Customer admin=new Customer();
        admin.setEmail("superadmin@email.com");
        admin.setPassword(encode.encode("adminPass"));
        customerRepo.save(admin);

        Customer savedAdmin=customerRepo.findByEmail("superadmin@email.com");
        RolesCustomer roleType=rolesRepo.findByRoleType("ROLE_ADMIN");
        Set<RolesCustomer> customersRoles=admin.getRoles();
        customersRoles.add(roleType);
        savedAdmin.setRoles(customersRoles);

        customerRepo.save(savedAdmin);

    }
}
