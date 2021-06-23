package com.nackademin.bookingSystem.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Hodei Eceiza
 * Date: 6/23/2021
 * Time: 22:17
 * Project: BookingSystem
 * Copyright: MIT
 */
@Data
@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @Transient
    private boolean isExpired;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName ="id")
    private Customer customer;

    public boolean isExpired(){
        return getExpireAt().isBefore(LocalDateTime.now());
    }
    private LocalDateTime expireAt;

    @CreationTimestamp
    private LocalDateTime createDate;
}
