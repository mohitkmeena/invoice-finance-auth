package com.mohit.invoice_financing_auth_service.dto;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class VerifyOtp {
    private String email;
    private Integer otp;
}