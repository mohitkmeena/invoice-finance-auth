package com.mohit.invoice_financing_auth_service.exceptions;


public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message){
        super(message);
    }
}

