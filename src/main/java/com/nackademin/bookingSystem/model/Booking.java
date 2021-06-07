package com.nackademin.bookingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Ashkan Amiri
 * Date:  2021-06-07
 * Time:  11:23
 * Project: BookingSystem
 * Copyright: MIT
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fromDate;

    @DateTimeFormat(pattern = "yy-MM-dd HH:mm")
    private LocalDateTime toDate;

    @ManyToMany(targetEntity= TimeProperties.class)
    @JoinTable(name="booking_time_Properties",
            joinColumns=@JoinColumn(name="booking_id"),
            inverseJoinColumns=@JoinColumn(name="timeProperties_id"))
    private List<TimeProperties> timeProperties;

    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;


    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime  modifyDate;

}
