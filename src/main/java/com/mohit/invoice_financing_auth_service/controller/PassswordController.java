package com.mohit.invoice_financing_auth_service.controller;

import com.mohit.invoice_financing_auth_service.dto.ResetPasswordDto;
import com.mohit.invoice_financing_auth_service.dto.VerifyOtp;
import com.mohit.invoice_financing_auth_service.service.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice/v1/password")
public class PassswordController {
    @Autowired
    private ForgetPasswordService forgetPasswordSecvice;

    @PostMapping("/verify")
    public String verifyEmail(@RequestParam String email){
        return forgetPasswordSecvice.verifyEmailAndSendOtp(email);
    }
    @PostMapping("/verify-otp")
    public String veriifyOtp(@RequestBody VerifyOtp verifyOtp ){

        return  forgetPasswordSecvice.verifyOtp(verifyOtp.getOtp(), verifyOtp.getEmail());
    }
    @PutMapping("/update-password")

    public String updatePassword(@RequestBody ResetPasswordDto resetPasswordDto){

        return  forgetPasswordSecvice.updatePassword(resetPasswordDto.getEmail(),resetPasswordDto.getPassword(), resetPasswordDto.getRpassword());
    }




}
