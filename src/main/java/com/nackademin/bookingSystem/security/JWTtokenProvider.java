package com.nackademin.bookingSystem.security;

import com.nackademin.bookingSystem.config.AppProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.Map;

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
@Component
public class JWTtokenProvider {

    //we will use some properties (expiration time and secret key)
    private AppProperties appProperties;

    public String createToken(Authentication authentication) {
        //get userdetails from authentication
        UserAuthenticated userPrincipal= (UserAuthenticated) authentication.getPrincipal();

        //set expire time
        Date now=new Date();
        Date expireDate=new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        //build the JWT
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("authority",userPrincipal.getAuthorities())
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth()
                        .getTokenSecret()).compact();
    }
    //Refresh token is the same as JWT token but has a longer expiration date.
    public String createRefreshToken(Map<String,Object> claims,String subject){
       // UserAuthenticated userPrincipal= (UserAuthenticated) authentication.getPrincipal();

        //set expire time
        Date now=new Date();
        Date expireDate=new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpirationMsec());

        //build the JWT
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
         //       .claim("authority",userPrincipal.getAuthorities())
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth()
                        .getTokenSecret()).compact();
    }

    public String getUserEmailFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    public boolean validateToken(String token){
        Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token);
        return true;
        //TODO: add some exception catching and return false.
    }
}
