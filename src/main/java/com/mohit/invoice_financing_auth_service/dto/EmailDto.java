package com.mohit.invoice_financing_auth_service.dto;

import lombok.Builder;
import lombok.Data;


@Builder
public record EmailDto(String to,String sub,String message) {
}
