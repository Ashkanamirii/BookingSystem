package com.nackademin.bookingSystem.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
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
        try {
            //get jwt
            String jwt = getJwt(httpServletRequest);
            //get String

            //if jwt not empty and passes validation
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                //get the email (we could get the social security number or the id)
                String userEmail = tokenProvider.getUserEmailFromJWT(jwt);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, SecurityContextHolder.getContext().getAuthentication(), userDetails.getAuthorities());


                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            //We check if its a refreshtoken request, and we allow it if its true
            String isRefreshToken = httpServletRequest.getHeader("isRefreshToken");
            String requestURL = httpServletRequest.getRequestURL().toString();
            if(isRefreshToken!=null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken"))
                allowRefreshToken(e,httpServletRequest);
            else
            httpServletRequest.setAttribute("exception", e);
        } catch (BadCredentialsException e) {
            httpServletRequest.setAttribute("exception", e);
            throw e;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
private void allowRefreshToken(ExpiredJwtException e, HttpServletRequest httpServletRequest){
        //we create an "empty" authentication and pass it to securityContext.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(null,null,null);
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    //we set the user data in the request, later we will get it in the controller
    httpServletRequest.setAttribute("claims",e.getClaims());
    }
    //we read the authorization header and save it in a string
    private String getJwt(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
