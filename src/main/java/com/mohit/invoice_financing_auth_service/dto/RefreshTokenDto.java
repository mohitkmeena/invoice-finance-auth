package com.mohit.invoice_financing_auth_service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RefreshTokenDto {
    private  String token;
}
