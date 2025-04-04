package com.mohit.invoice_financing_auth_service.producer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEventDto {
    private String email;
    private String phoneNumber;

}
