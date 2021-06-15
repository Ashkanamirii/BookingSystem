package com.nackademin.bookingSystem.model;


import javax.annotation.processing.Generated;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ashkan Amiri
 * Date:  2021-06-07
 * Time:  11:23
 * Project: BookingSystem
 * Copyright: MIT
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
//password and security number are tagged with JsonIgnore, so, no one can see those details.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @JsonIgnore
    @Column(unique = true)
    private Long securityNumber;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private boolean isAdmin;
    private boolean isUser;

    @OneToMany(targetEntity = Booking.class)
    private List<Booking> bookingList;




    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime  modifyDate;


}
