package com.nackademin.bookingSystem.model;


import javax.annotation.processing.Generated;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private Long securityNumber;

    @Column(unique = true)
    private String email;

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
