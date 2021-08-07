package com.nackademin.bookingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Created by Hodei Eceiza
 * Date: 6/9/2021
 * Time: 22:27
 * Project: BookingSystem
 * Copyright: MIT
 */
@Data
@AllArgsConstructor
public class SignUpReq {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private Long securityNumber;
}
