package com.mohit.invoice_financing_auth_service.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ResetPasswordDto {
    private String email,password,rpassword;
}