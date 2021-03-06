package com.nackademin.bookingSystem.controller;

import com.nackademin.bookingSystem.config.AppProperties;
import com.nackademin.bookingSystem.dto.LoginReq;
import com.nackademin.bookingSystem.dto.ResetPassReq;
import com.nackademin.bookingSystem.dto.SignUpReq;
import com.nackademin.bookingSystem.model.Customer;
import com.nackademin.bookingSystem.model.VerificationToken;
import com.nackademin.bookingSystem.security.JWTtokenProvider;
import com.nackademin.bookingSystem.dto.JwtAuthResponse;
import com.nackademin.bookingSystem.service.CustomerService;

import com.nackademin.bookingSystem.service.VerificationTokenService;
import com.nackademin.bookingSystem.service.email.AccountVerificationEmail;
import com.nackademin.bookingSystem.service.email.CustomEmailService;
import com.nackademin.bookingSystem.service.email.ResetPassEmail;
import io.jsonwebtoken.impl.DefaultClaims;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


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
        return ResponseEntity.ok().body("Verification email sent to " + signUpReq.getEmail());
    }

    private void sendVerificationEmail(Customer customer) throws MessagingException {

        AccountVerificationEmail email = new AccountVerificationEmail(appProperties);
        email.init(customer);
        VerificationToken verificationToken = verificationTokenService.createVerificationToken(customer);

        email.setToken(verificationToken.getToken());
        email.buildVerificationUrl(appProperties.getRedirections().getBaseUri(), verificationToken.getToken());

        emailService.sendHtmlFormattedEmail(email);
    }

    @GetMapping("verify/{token}")
    public ResponseEntity<?> verifyAccount(@PathVariable String token) {

        VerificationToken verificationToken = verificationTokenService.findByToken(token);

        if (verificationToken.isExpired() || !verificationToken.getToken().equals(token)) {
            return ResponseEntity.badRequest().body("Secure token not accepted");
        } else {
            Customer customer = verificationToken.getCustomer();
            customer.setAccountVerified(true);
            customerService.updateCustomer(customer);
            verificationTokenService.removeToken(verificationToken);
            return ResponseEntity.ok().body("User accepted, go to login");
        }
    }

    @PostMapping("/resetpass/{email}")
    public ResponseEntity<?> sendResetPassEmail(@PathVariable String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        try {
            sendResetPassEmail(customer);
        } catch (MessagingException e) {
            return ResponseEntity.badRequest().body("couldn't send email");
        }
        return ResponseEntity.ok().body("Reset password mail sent");
    }

    private void sendResetPassEmail(Customer customer) throws MessagingException {

        ResetPassEmail email = new ResetPassEmail(appProperties);
        email.init(customer);

        VerificationToken verificationToken = verificationTokenService.createVerificationToken(customer);

        email.setToken(verificationToken.getToken());

        email.buildVerificationUrl(appProperties.getRedirections().getBaseUri(), verificationToken.getToken(), customer.getEmail());

        emailService.sendHtmlFormattedEmail(email);
    }


    @PostMapping("/renewpass")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPassReq resetPass) {

        VerificationToken verificationToken = verificationTokenService.findByToken(resetPass.getResetToken());

        if (verificationToken.isExpired() || !verificationToken.getToken().equals(resetPass.getResetToken())) {
            return ResponseEntity.badRequest().body("couldn't renew your password");
        } else {
            Customer customer = verificationToken.getCustomer();
            String encodedPass = passwordEncoder.encode(resetPass.getNewPassword());
            customer.setPassword(encodedPass);
            customerService.updateCustomer(customer);
            verificationTokenService.removeToken(verificationToken);
            return ResponseEntity.ok().body("Password renewed");
        }

    }

    @GetMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        DefaultClaims claims = (DefaultClaims) request.getAttribute("claims");
        Map<String, Object> expected = getMapFromJWT(claims);
        String token = tokenProvider.createRefreshToken(expected, expected.get("sub").toString());
        return ResponseEntity.ok().body(token);
    }

    public Map<String, Object> getMapFromJWT(DefaultClaims claims) {
        Map<String, Object> expected = new HashMap<>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expected.put(entry.getKey(), entry.getValue());
        }
        return expected;
    }
}
