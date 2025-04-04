package com.mohit.invoice_financing_auth_service.service;

import com.mohit.invoice_financing_auth_service.dto.EmailDto;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    public void sendMail(EmailDto mailBody);
}
