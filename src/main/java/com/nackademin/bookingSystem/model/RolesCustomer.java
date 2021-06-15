package com.nackademin.bookingSystem.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Hodei Eceiza
 * Date: 6/15/2021
 * Time: 22:21
 * Project: BookingSystem
 * Copyright: MIT
 */
@Data
@Entity
public class RolesCustomer {
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
private Long id;
private String roleType;
}
