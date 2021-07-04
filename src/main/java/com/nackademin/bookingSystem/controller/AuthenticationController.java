package com.nackademin.bookingSystem.controller;

import com.nackademin.bookingSystem.config.AppProperties;
import com.nackademin.bookingSystem.dto.LoginReq;
import com.nackademin.bookingSystem.dto.SignUpReq;
import com.nackademin.bookingSystem.model.Customer;
import com.nackademin.bookingSystem.model.VerificationToken;
import com.nackademin.bookingSystem.security.JWTtokenProvider;
import com.nackademin.bookingSystem.dto.JwtAuthResponse;
import com.nackademin.bookingSystem.service.CustomerService;

import com.nackademin.bookingSystem.service.VerificationTokenService;
import com.nackademin.bookingSystem.service.email.AccountVerificationEmail;
import com.nackademin.bookingSystem.service.email.CustomEmailService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Objects;



/**
 * Created by Hodei Eceiza
 * Date: 6/9/2021
 * Time: 22:14
 * Project: BookingSystem
 * Copyright: MIT
 */
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JWTtokenProvider tokenProvider;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private CustomEmailService emailService;

    @Autowired
    private AppProperties appProperties;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq loginReq) {
        if (!customerService.emailExists(loginReq.getEmail())) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Email not found"); //418

        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginReq.getEmail(),
                        loginReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //create token
        String token = tokenProvider.createToken(authentication);

        //set ok response
        return ResponseEntity.ok(new JwtAuthResponse(token));

    }

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpReq signUpReq) {

        if (customerService.emailExists(signUpReq.getEmail())) {
            return ResponseEntity.status(403).body("Email is already signed in the database");

        }

        Customer customer = new Customer();
        customer.setFirstName(signUpReq.getFirstName());
        customer.setLastName(signUpReq.getLastName());
        customer.setEmail(signUpReq.getEmail());
        customer.setSecurityNumber(signUpReq.getSecurityNumber());
        customer.setPassword(passwordEncoder.encode(signUpReq.getPassword()));
        customer.setAccountVerified(false);
        customerService.addCustomerAsUser(customer);

        try {
            sendVerificationEmail(customer);
        } catch (MessagingException e) {
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Couldn't send verification email");
        }
        return ResponseEntity.ok().body("Verification email sent to "+signUpReq.getEmail());
    }

    private void sendVerificationEmail(Customer customer) throws MessagingException {

        AccountVerificationEmail email=new AccountVerificationEmail(appProperties);
        email.init(customer);
        VerificationToken tokenTest =verificationTokenService.createVerificationToken(customer);


        email.setToken(tokenTest.getToken());
        email.buildVerificationUrl(appProperties.getRedirections().getBaseUri(),tokenTest.getToken());

        emailService.sendHtmlFormattedEmail(email);


    }
    @GetMapping("verify/{token}")
    public ResponseEntity<?> verifyAccount(@PathVariable String token){

        VerificationToken verificationToken=verificationTokenService.findByToken(token);


        if(verificationToken.isExpired() || !verificationToken.getToken().equals(token) || Objects.isNull(verificationToken)){
            return ResponseEntity.badRequest().body("Secure token not accepted");
        }
        else{
           Customer customer= verificationToken.getCustomer();

           customer.setAccountVerified(true);

            customerService.updateCustomer(customer);


                verificationTokenService.removeToken(verificationToken);



            return ResponseEntity.ok().body("User accepted, go to login");
        }
    }

}
