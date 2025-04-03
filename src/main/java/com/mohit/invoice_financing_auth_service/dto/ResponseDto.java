package com.mohit.invoice_financing_auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private String message;
    private String code;
}
