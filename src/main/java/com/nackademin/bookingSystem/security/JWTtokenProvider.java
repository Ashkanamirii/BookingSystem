package com.nackademin.bookingSystem.security;

import com.nackademin.bookingSystem.config.AppProperties;
import com.sun.security.auth.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Hodei Eceiza
 * Date: 6/9/2021
 * Time: 00:27
 * Project: BookingSystem
 * Copyright: MIT
 */

/**
 * to create a JWT token
 */
@AllArgsConstructor
@Service
public class JWTtokenProvider {

    //we will use some properties (expiration time and secret key)
    private AppProperties appProperties;

    public String createToken(Authentication authentication) {
        //get userdetails from authentication
        UserPrincipal userPrincipal=(UserPrincipal)authentication.getPrincipal();
        //set expire time
        Date now=new Date();
        Date expireDate=new Date(now.getTime() + appProperties.getAuth().getTokenExpirationsMsec());

        //build the JWT
        return Jwts.builder().setSubject(userPrincipal.getName()).setIssuedAt(new Date()).setExpiration(expireDate).signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret()).compact();
    }

    public boolean validateToken(String token){
        Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token);
        return true;
        //TODO: add some exception catching and return false.
    }
}
