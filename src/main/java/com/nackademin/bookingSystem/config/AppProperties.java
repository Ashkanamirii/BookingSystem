package com.nackademin.bookingSystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Created by Hodei Eceiza
 * Date: 6/7/2021
 * Time: 22:47
 * Project: BookingSystem
 * Copyright: MIT
 */
//this is to read everything inside "app" in properties, which basically has properties to create a JWT
@Getter
@ConfigurationProperties(prefix="app")
public class AppProperties {
    private final Auth auth=new Auth();
    private final Oauth2 oauth2=new Oauth2();
    private final Redirections redirections=new Redirections();

    @Getter
    @Setter
    public static class Auth {
        private String tokenSecret;
        private Long tokenExpirationMsec;
    }
    @Getter
    @Setter
    public static final class Oauth2{
        //we redirect only to one place, but we can check other places maybe??
        private String authorizedRedirectUris;

    }
    @Getter
    @Setter
    public static class Redirections{
        private String baseUri;
        private String loginRedirect;
        private String verificationRedirect;
        private String resetPassRedirect;
    }
}
