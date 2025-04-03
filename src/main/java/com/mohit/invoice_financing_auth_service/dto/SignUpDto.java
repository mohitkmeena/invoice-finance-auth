package com.mohit.invoice_financing_auth_service.dto;


import lombok.Data;

@Data
public class SignUpDto {

    private String email;
    private String phoneNumber;
    private  String password;
    private String role;
}
