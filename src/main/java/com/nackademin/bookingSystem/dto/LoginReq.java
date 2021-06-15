package com.nackademin.bookingSystem.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Created by Hodei Eceiza
 * Date: 6/9/2021
 * Time: 22:23
 * Project: BookingSystem
 * Copyright: MIT
 */
@Data
public class LoginReq {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
