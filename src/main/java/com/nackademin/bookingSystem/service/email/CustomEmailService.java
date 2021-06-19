package com.nackademin.bookingSystem.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;

/**
 * Created by Hodei Eceiza
 * Date: 6/19/2021
 * Time: 23:07
 * Project: BookingSystem
 * Copyright: MIT
 */
@Service
public class CustomEmailService implements EmailService{

    @Autowired
    public JavaMailSender emailSender;
    @Override
    public void sendEmail(String toAddress, String subject, String message) {
        SimpleMailMessage simpleMessage= new SimpleMailMessage();
        simpleMessage.setTo(toAddress);
        simpleMessage.setSubject(subject);
        simpleMessage.setText(message);
        emailSender.send(simpleMessage);
    }

    @Override
    public void sendEmailAndAttachment(String toAddress, String subject, String message, String attachment) throws MessagingException, FileNotFoundException {
        MimeMessage emailToSend= emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(emailToSend, true);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));
        messageHelper.addAttachment("Purchase Order", file);
        emailSender.send(emailToSend);
    }
}
