package com.mohit.invoice_financing_auth_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    public  String generateToken(String userName);
    public String extractUsername(String token);
    public boolean isTokenValid(String token, UserDetails userDetails);
}
