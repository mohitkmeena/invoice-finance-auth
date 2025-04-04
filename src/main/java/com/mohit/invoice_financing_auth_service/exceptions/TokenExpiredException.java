package com.mohit.invoice_financing_auth_service.exceptions;

public class TokenExpiredException extends RuntimeException{

    public TokenExpiredException(String message){
        super(message);
    }
}
