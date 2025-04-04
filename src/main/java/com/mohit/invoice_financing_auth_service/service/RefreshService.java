package com.mohit.invoice_financing_auth_service.service;

import com.mohit.invoice_financing_auth_service.entity.RefreshToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RefreshService {
    public RefreshToken createRefreshToken(String username);
    public RefreshToken validaRefreshToken(RefreshToken token);
    public Optional<RefreshToken> findByToken(String token);
}
