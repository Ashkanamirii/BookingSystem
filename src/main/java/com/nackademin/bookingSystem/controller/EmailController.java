package com.nackademin.bookingSystem.controller;

import com.nackademin.bookingSystem.config.AppProperties;
import com.nackademin.bookingSystem.model.Customer;
import com.nackademin.bookingSystem.model.VerificationToken;
import com.nackademin.bookingSystem.service.VerificationTokenService;
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
//this controller is for test until we get more need for email service
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    EmailService emailService;
    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    private AppProperties appProperties;

    @GetMapping(value = "/test-email/{user-email}")
    public
    ResponseEntity<?> sendSimpleEmail(@PathVariable("user-email") String email) {

        try {
            emailService.sendSimpleEmail(email, "Welcome", "This is a welcome test email for you!!");
        } catch (MailException mailException) {
            return  ResponseEntity.badRequest().body("Unable to send email" + mailException.getStackTrace());
        }

        return ResponseEntity.ok("Please check your inbox");
    }
    //For test, nothing specifically useful...
    @PostMapping(value = "/test-email/test-html")
    public
    ResponseEntity<?> sendHtmlEmail(@RequestBody Customer customer) {

        AccountVerificationEmail email=new AccountVerificationEmail(appProperties);
        email.init(customer);
        VerificationToken tokenTest =verificationTokenService.createVerificationToken(customer);
        email.setToken(tokenTest.getToken());
        email.buildVerificationUrl("http://google.com",tokenTest.getToken());


        try {
            emailService.sendHtmlFormattedEmail(email);
        } catch (MessagingException e) {
            return  ResponseEntity.badRequest().body("Unable to send email");
        }

        return ResponseEntity.ok("Please check your inbox");
    }
}
