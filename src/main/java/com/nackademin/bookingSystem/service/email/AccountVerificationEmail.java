package com.nackademin.bookingSystem.service.email;

import com.nackademin.bookingSystem.model.Customer;
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
}
