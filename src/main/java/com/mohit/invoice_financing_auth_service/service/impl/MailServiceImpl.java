package com.mohit.invoice_financing_auth_service.service.impl;

import com.mohit.invoice_financing_auth_service.dto.EmailDto;
import com.mohit.invoice_financing_auth_service.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;
    @Override
    public void sendMail(EmailDto mailBody) {
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(mailBody.to());
        mailMessage.setFrom(sender);
        mailMessage.setSubject(mailBody.sub());
        mailMessage.setText(mailBody.message());
        javaMailSender.send(mailMessage);
    }
}
