package com.nackademin.bookingSystem.service.email;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

/**
 * Created by Hodei Eceiza
 * Date: 6/19/2021
 * Time: 23:07
 * Project: BookingSystem
 * Copyright: MIT
 */
public interface EmailService {
    void sendEmail(String toAddress, String subject, String message);
    void sendEmailAndAttachment(String toAddress, String subject,String message, String attachment)throws MessagingException, FileNotFoundException;
}
