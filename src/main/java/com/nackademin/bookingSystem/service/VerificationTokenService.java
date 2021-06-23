package com.nackademin.bookingSystem.service;

import com.nackademin.bookingSystem.model.VerificationToken;
import com.nackademin.bookingSystem.repository.VerificationTokenRepo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.time.LocalDateTime;

/**
 * Created by Hodei Eceiza
 * Date: 6/23/2021
 * Time: 22:37
 * Project: BookingSystem
 * Copyright: MIT
 */
@Service
public class VerificationTokenService{
    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(30);

    @Autowired
    private VerificationTokenRepo verificationTokenRepo;

    public VerificationToken createVerificationToken(){
        String token=new String(Base64.encodeBase64( DEFAULT_TOKEN_GENERATOR.generateKey(), true, true ));
        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setExpireAt(LocalDateTime.now().plusHours(24));//we give one day
        verificationTokenRepo.save(verificationToken);
        return verificationToken;
    }
}
