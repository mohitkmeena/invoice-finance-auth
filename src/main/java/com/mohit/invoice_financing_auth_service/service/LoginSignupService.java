package com.mohit.invoice_financing_auth_service.service;

import com.mohit.invoice_financing_auth_service.dto.LoginDto;
import com.mohit.invoice_financing_auth_service.dto.ResponseDto;
import com.mohit.invoice_financing_auth_service.dto.SignUpDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LoginSignupService {
    public ResponseEntity<ResponseDto> login(LoginDto loginDto);
    public ResponseEntity<ResponseDto> signup(SignUpDto signUpDto);
    public   String getUserIdByUsername(String username);
}
