package com.nackademin.bookingSystem.security;

import com.nackademin.bookingSystem.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

/**
 * Created by Hodei Eceiza
 * Date: 6/8/2021
 * Time: 23:54
 * Project: BookingSystem
 * Copyright: MIT
 */
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    @Autowired
    CustomerRepo customerRepo;

}
