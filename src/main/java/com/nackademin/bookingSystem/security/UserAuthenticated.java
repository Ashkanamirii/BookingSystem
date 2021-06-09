package com.nackademin.bookingSystem.security;

import com.nackademin.bookingSystem.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hodei Eceiza
 * Date: 6/9/2021
 * Time: 12:33
 * Project: BookingSystem
 * Copyright: MIT
 */
@Data
@AllArgsConstructor
public class UserAuthenticated implements UserDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private Long securityNumber;
    private String email;
    private String password;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    public UserAuthenticated(String email, String password, Collection<? extends GrantedAuthority> singletonList) {
        this.email=email;
        this.password=password;
        this.authorities=singletonList;
    }


    public static UserDetails create(Customer customer) {
           return new UserAuthenticated
            (customer.getEmail(), customer.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(("ROLE_USER"))));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //for now there is no roles fixed.
        return Collections.singletonList(new SimpleGrantedAuthority(("ROLE_USER")));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public String getName(){
      return email;
    }
}
