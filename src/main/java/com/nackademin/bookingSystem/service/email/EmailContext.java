package com.nackademin.bookingSystem.service.email;

import com.nackademin.bookingSystem.model.Customer;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hodei Eceiza
 * Date: 6/21/2021
 * Time: 22:39
 * Project: BookingSystem
 * Copyright: MIT
 */
@Data
public abstract class EmailContext {
    private String from;
    private String to;
    private String subject;
    private String email;
    private String attachment;
    private String fromDisplayName;
    private String emailLanguage;
    private String displayName;
    private String templateLocation;
    private Map<String, Object> context;

    public EmailContext() {
        this.context = new HashMap<>();
    }

    public <T> void init(T context){

    }

}
