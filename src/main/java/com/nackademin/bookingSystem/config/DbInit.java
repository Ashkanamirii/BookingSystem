package com.nackademin.bookingSystem.config;

import com.nackademin.bookingSystem.model.Customer;
import com.nackademin.bookingSystem.model.RolesCustomer;
import com.nackademin.bookingSystem.repository.CustomerRepo;
import com.nackademin.bookingSystem.repository.RolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private AppProperties environment;
    @PostConstruct
    private void postConstruct(){


        if(!customerRepo.existsByEmail(environment.getDbinserts().getMasterAdminEmail())) {
            BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
            Customer admin = new Customer();
            admin.setEmail(environment.getDbinserts().getMasterAdminEmail());
            admin.setPassword(encode.encode(environment.getDbinserts().getMasterAdminPass()));
            admin.setAccountVerified(true);
            customerRepo.save(admin);

            Customer savedAdmin = customerRepo.findByEmail(environment.getDbinserts().getMasterAdminEmail());
            RolesCustomer roleType = rolesRepo.findByRoleType("ROLE_ADMIN");
            Set<RolesCustomer> customersRoles = admin.getRoles();
            customersRoles.add(roleType);
            savedAdmin.setRoles(customersRoles);

            customerRepo.save(savedAdmin);
        }

    }
}
