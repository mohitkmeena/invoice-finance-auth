package com.mohit.invoice_financing_auth_service.controller;

import com.mohit.invoice_financing_auth_service.dto.LoginDto;
import com.mohit.invoice_financing_auth_service.dto.ResponseDto;
import com.mohit.invoice_financing_auth_service.dto.SignUpDto;
import com.mohit.invoice_financing_auth_service.service.LoginSignupService;
import com.mohit.invoice_financing_auth_service.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice/v1/auth")
public class AuthController {
    @Autowired(required = true)
    private LoginSignupService userDetails;

    @PostMapping ("signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody SignUpDto signUpDto){
        return userDetails.signup(signUpDto);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDto>loginDto(@RequestBody LoginDto loginDto){
        return userDetails.login(loginDto);
    }

}
