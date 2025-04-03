package com.mohit.invoice_financing_auth_service.dto;

import com.mohit.invoice_financing_auth_service.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserDto {

    private String email;
    private String phoneNumber;
    private  String password;
    private String role;

}
