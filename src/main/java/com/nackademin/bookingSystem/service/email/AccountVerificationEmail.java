package com.nackademin.bookingSystem.service.email;

import com.nackademin.bookingSystem.config.AppProperties;
import com.nackademin.bookingSystem.model.Customer;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * Created by Hodei Eceiza
 * Date: 6/21/2021
 * Time: 23:26
 * Project: BookingSystem
 * Copyright: MIT
 */
@AllArgsConstructor
public class AccountVerificationEmail extends EmailContext {
    @Autowired
    private AppProperties appProperties;
    @Override
    public <T> void init(T context){

        //TODO: fix with custom data
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
                .path(appProperties.getRedirections().getVerificationRedirect()).queryParam("token", token).toUriString();

        super.getContext().put("verificationURL", url);
    }
}
