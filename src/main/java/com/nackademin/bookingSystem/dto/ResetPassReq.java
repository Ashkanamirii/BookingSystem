package com.nackademin.bookingSystem.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Created by Hodei Eceiza
 * Date: 7/5/2021
 * Time: 10:07
 * Project: BookingSystem
 * Copyright: MIT
 */

@Data
@AllArgsConstructor
public class ResetPassReq {
    private String resetToken;
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String newPassword;

}
