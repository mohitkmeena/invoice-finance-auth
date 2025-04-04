package com.mohit.invoice_financing_auth_service.service;

import org.springframework.stereotype.Service;

@Service
public interface ForgetPasswordService {
    public String verifyEmailAndSendOtp(String email);
    public String verifyOtp(Integer otp,String email);
    public String updatePassword(String email,String password,String rpassword);
}
