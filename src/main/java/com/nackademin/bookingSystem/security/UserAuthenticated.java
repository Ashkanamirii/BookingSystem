package com.nackademin.bookingSystem.security;

import com.nackademin.bookingSystem.model.Customer;

import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Hodei Eceiza
 * Date: 6/9/2021
 * Time: 12:33
 * Project: BookingSystem
 * Copyright: MIT
 */
//@Getter
@Data
//@AllArgsConstructor
public class UserAuthenticated implements UserDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private Long securityNumber;
    private String email;
    private String password;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    public UserAuthenticated(Long id, String firstName, String lastName, Long securityNumber, String email, String password, String username, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.securityNumber = securityNumber;
        this.email = email;
        this.password = password;
        this.username = username;
        this.authorities = authorities;
    }

    public UserAuthenticated(String email, String password, Collection<? extends GrantedAuthority> singletonList) {
        this.email=email;
        this.password=password;
        this.authorities=singletonList;
    }


    public static UserDetails create(Customer customer) {

           return new UserAuthenticated
            (customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getSecurityNumber(),
                    customer.getEmail(),
                    customer.getPassword(),
                    customer.getEmail(),
                   customerAuthorities(customer));

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //for now there is no roles fixed.
       // return Collections.singletonList(new SimpleGrantedAuthority(("ROLE_USER")));
        return authorities;
    }
    private static Collection<GrantedAuthority> customerAuthorities(Customer customer){
        Collection<GrantedAuthority>authorities=new ArrayList<>();
        customer.getRoles().stream().forEach(r->authorities.add(new SimpleGrantedAuthority(r.getRoleType())));
        return authorities;

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
        return true;
    }

    /*public String getName(){
      return email;
    }*/
}
