package com.nackademin.bookingSystem;

import com.nackademin.bookingSystem.config.AppProperties;

import com.nackademin.bookingSystem.config.DbInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


//comment -> (exclude ....) if we want to use spring security
@SpringBootApplication//(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties(AppProperties.class)
public class BookingSystemApplication {
    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
    public static void main(String[] args) {
        SpringApplication.run(BookingSystemApplication.class, args);
    }


}
