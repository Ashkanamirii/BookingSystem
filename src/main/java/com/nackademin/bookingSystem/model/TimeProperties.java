package com.nackademin.bookingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Created by Ashkan Amiri
 * Date:  2021-06-07
 * Time:  11:24
 * Project: BookingSystem
 * Copyright: MIT
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TimeProperties {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    private double price;
//    private String timeRange; //20-21
//    private String day;
//    @NotNull
//    @Size()
    private String availableSlots;
    //private Club club; Name, nameOfplane



    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime  modifyDate;

}
