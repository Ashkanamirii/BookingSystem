package com.nackademin.bookingSystem.service.email;

import com.nackademin.bookingSystem.model.Customer;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.context.Context;

/**
 * Created by Hodei Eceiza
 * Date: 6/21/2021
 * Time: 23:26
 * Project: BookingSystem
 * Copyright: MIT
 */
public class AccountVerificationEmail extends EmailContext {
    @Override
    public <T> void init(T context){

        //TODO: fix this approach
        Customer customer=(Customer) context;
       super.getContext().put("firstName", customer.getFirstName());
        setTemplateLocation("email/AccountVerification");
        setSubject("Complete your registration");
        setFrom("test@email.com");
        setTo(customer.getEmail());

    }
    public void setToken(String token) {

        super.getContext().put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token){
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/register/verify").queryParam("token", token).toUriString();
        super.getContext().put("verificationURL", url);
    }
}
