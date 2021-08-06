package com.nackademin.bookingSystem.service.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by Hodei Eceiza
 * Date: 8/6/2021
 * Time: 23:39
 * Project: BookingSystem
 * Copyright: MIT
 */
@Configuration
public class EmailConf {
    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
}
