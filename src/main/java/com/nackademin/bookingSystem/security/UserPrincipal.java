package com.nackademin.bookingSystem.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import java.util.Collection;

/**
 * Created by Hodei Eceiza
 * Date: 6/9/2021
 * Time: 12:33
 * Project: BookingSystem
 * Copyright: MIT
 */

//TODO: fill up and fix it
public class UserPrincipal implements UserDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private Long securityNumber;
    private String email;
    private String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
