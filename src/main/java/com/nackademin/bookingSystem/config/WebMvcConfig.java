package com.nackademin.bookingSystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

/**
 * Created by Hodei Eceiza
 * Date: 6/7/2021
 * Time: 23:10
 * Project: BookingSystem
 * Copyright: MIT
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //uncomment when security is set up
    @Value("${spring.mail.host}")
    private static final String MAIL_HOST = null;
    @Value("${spring.mail.username}")
    private static final String MAIL_USERNAME = null;
    @Value("${spring.mail.password}")
    private static final String MAIL_PASS=null;
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(MAIL_HOST);
        mailSender.setPort(587);

        mailSender.setUsername(MAIL_USERNAME);
        mailSender.setPassword(MAIL_PASS);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**")
                //.allowedOrigins("/**")//we allow any origin, maybe we fix only for the client???
                .allowedOriginPatterns("*")//we allow any origin, maybe we fix only for the client???
                .allowedMethods("GET","PUT","POST","DELETE","PATCH","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);//in spring docs they usually set one hour of max age, don't know whats better
    }
}
