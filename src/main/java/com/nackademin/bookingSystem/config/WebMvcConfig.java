package com.nackademin.bookingSystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
