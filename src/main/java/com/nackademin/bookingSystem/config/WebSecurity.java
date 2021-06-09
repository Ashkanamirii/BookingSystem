package com.nackademin.bookingSystem.config;

import com.nackademin.bookingSystem.security.CustomOauth2UserService;
import com.nackademin.bookingSystem.security.CustomUserDetailsService;
import com.nackademin.bookingSystem.security.JWTtokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Hodei Eceiza
 * Date: 6/8/2021
 * Time: 23:49
 * Project: BookingSystem
 * Copyright: MIT
 */
@Configuration
@EnableWebSecurity //uncomment to run security
//global methods will be useful to allow users depending their role
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurity extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomOauth2UserService customOauth2UserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/authenticate/**").permitAll()
                //.antMatchers("/authenticate/signup").permitAll()
                .anyRequest().authenticated()
               ;
        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Override //here I used BCRYPT for hashing (with salt), will check whats better (md5 or bcrypt)
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public JWTtokenFilter tokenFilter(){
        return new JWTtokenFilter();
    }
}
