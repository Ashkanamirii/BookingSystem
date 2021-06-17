package com.nackademin.bookingSystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Hodei Eceiza
 * Date: 6/9/2021
 * Time: 00:28
 * Project: BookingSystem
 * Copyright: MIT
 */
public class JWTtokenFilter extends OncePerRequestFilter {
    @Autowired
    private JWTtokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //get jwt
        String jwt=getJwt(httpServletRequest);
        //get String

        //if jwt not empty and passes validation
            if(StringUtils.hasText(jwt)&& tokenProvider.validateToken(jwt)){
                //get the email (we could get the social security number or the id)
                String userEmail=tokenProvider.getUserEmailFromJWT(jwt);
                UserDetails userDetails= customUserDetailsService.loadUserByUsername(userEmail);
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,SecurityContextHolder.getContext().getAuthentication(), userDetails.getAuthorities());


                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    //we read the authorization header and save it in a string
    private String getJwt(HttpServletRequest request){
        String bearer=request.getHeader("Authorization");
        if(StringUtils.hasText(bearer)&& bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }
}
