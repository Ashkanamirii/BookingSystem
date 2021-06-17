package com.nackademin.bookingSystem.model;



import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.*;

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

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name="customer_roles",joinColumns =@JoinColumn(name="customer_id"),inverseJoinColumns = @JoinColumn(name="roles_id"))
    private Set<RolesCustomer> roles=new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime  modifyDate;


}
