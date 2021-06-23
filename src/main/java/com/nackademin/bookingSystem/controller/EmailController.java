package com.nackademin.bookingSystem.controller;

import com.nackademin.bookingSystem.model.Customer;
import com.nackademin.bookingSystem.service.email.AccountVerificationEmail;
import com.nackademin.bookingSystem.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;

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
    @PostMapping(value = "/test-email/test-html")
    public
    ResponseEntity<?> sendHtmlEmail(@RequestBody Customer customer) {
        AccountVerificationEmail email=new AccountVerificationEmail();
        email.init(customer);
        email.setToken("Test token");
        email.buildVerificationUrl("http://google.com","Test token");


        try {
            emailService.sendHtmlFormattedEmail(email);
        } catch (MessagingException e) {
            return  ResponseEntity.badRequest().body("Unable to send email");
        }

        return ResponseEntity.ok("Please check your inbox");
    }
}
