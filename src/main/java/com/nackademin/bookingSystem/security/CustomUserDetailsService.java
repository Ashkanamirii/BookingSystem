package com.nackademin.bookingSystem.security;

import com.nackademin.bookingSystem.model.Customer;
import com.nackademin.bookingSystem.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

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
//we set email as main name, the password, and we add an grantedAuthority,... will check how to implement roles.
        return new User(customer.getEmail(), customer.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(("ROLE_USER"))));
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
