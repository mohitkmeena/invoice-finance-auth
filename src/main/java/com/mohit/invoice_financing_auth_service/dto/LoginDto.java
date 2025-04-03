package com.mohit.invoice_financing_auth_service.dto;

import lombok.Data;

@Data
public class LoginDto {

    private String emailOrPhonenUmber;

    private  String password;
}
