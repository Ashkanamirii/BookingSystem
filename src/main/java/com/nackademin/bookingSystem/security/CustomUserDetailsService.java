package com.nackademin.bookingSystem.security;

import com.nackademin.bookingSystem.model.Customer;
import com.nackademin.bookingSystem.repository.CustomerRepo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/**
 * Created by Hodei Eceiza
 * Date: 6/8/2021
 * Time: 23:54
 * Project: BookingSystem
 * Copyright: MIT
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    CustomerRepo customerRepo;
    //TODO: decide use name or email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByEmail(email);
        return UserAuthenticated.create(customer);
    }

    //in case we use a customRoles entity
   /* private Collection<GrantedAuthority> getAuthorities(CustomUser user) {
        Set<CustomRoles> userRoles = user.getRoles();
        Collection<GrantedAuthority> authorities = new ArrayList<>(userRoles.size());
        for (CustomRoles usersRoles : userRoles) {
            authorities.add(new SimpleGrantedAuthority(usersRoles.getRoleType().toUpperCase()));
            System.out.println(usersRoles.getRoleType().toUpperCase());
        }

        return authorities;
    }*/
}
