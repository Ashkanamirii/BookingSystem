package com.nackademin.bookingSystem.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Created by Hodei Eceiza
 * Date: 7/5/2021
 * Time: 10:07
 * Project: BookingSystem
 * Copyright: MIT
 */
@Getter
@Setter
@ToString
public class ResetPassReq {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String newPassword;
    @NotBlank
    private String token;
}
