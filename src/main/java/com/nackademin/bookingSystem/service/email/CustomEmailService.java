package com.nackademin.bookingSystem.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

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

    @Autowired
    public SpringTemplateEngine springTemplate;
    @Override
    public void sendSimpleEmail(String toAddress, String subject, String message) {
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

    @Override
    public void sendHtmlFormatedEmail(EmailContext emailContext) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        //format for html
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        //get the html
        Context context= new Context();
        context.setVariables(emailContext.getContext());
        String emailContent =springTemplate.process(emailContext.getTemplateLocation(),context);

        //fill up with data and send
        mimeMessageHelper.setTo(emailContext.getTo());
        mimeMessageHelper.setSubject(emailContext.getSubject());
        mimeMessageHelper.setFrom(emailContext.getFrom());
        mimeMessageHelper.setText(emailContent, true);

        emailSender.send(message);
    }
}
