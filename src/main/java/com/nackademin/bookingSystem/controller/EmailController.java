package com.nackademin.bookingSystem.controller;

import com.nackademin.bookingSystem.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hodei Eceiza
 * Date: 6/21/2021
 * Time: 23:50
 * Project: BookingSystem
 * Copyright: MIT
 */
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    EmailService emailService;

    @GetMapping(value = "/test-email/{user-email}")
    public
    ResponseEntity<?> sendSimpleEmail(@PathVariable("user-email") String email) {

        try {
            emailService.sendSimpleEmail(email, "Welcome", "This is a welcome test email for you!!");
        } catch (MailException mailException) {
            return  ResponseEntity.badRequest().body("Unable to send email");
        }

        return ResponseEntity.ok("Please check your inbox");
    }
}
