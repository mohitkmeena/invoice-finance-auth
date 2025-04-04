package com.mohit.invoice_financing_auth_service.service.impl;

import com.mohit.invoice_financing_auth_service.entity.RefreshToken;
import com.mohit.invoice_financing_auth_service.entity.User;
import com.mohit.invoice_financing_auth_service.exceptions.TokenExpiredException;
import com.mohit.invoice_financing_auth_service.exceptions.UserNotFoundException;
import com.mohit.invoice_financing_auth_service.repository.RefreshTokenRepository;
import com.mohit.invoice_financing_auth_service.repository.UserRepository;
import com.mohit.invoice_financing_auth_service.service.RefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Service
public class RefreshServiceImpl implements RefreshService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${app.refresh-token-expiration}")
    private Long REFRESH_TOKEN_EXPIRATION;
    @Override
    public RefreshToken createRefreshToken(String username) {
        User user=userRepository.findUser(username).orElseThrow(()->new UserNotFoundException("user not found with given username "+username ));
        RefreshToken refreshToken=  refreshTokenRepository.getByUser(user);
        if(refreshToken==null){
            refreshToken= RefreshToken .builder()
                    .user(user)
                    .expirationtime(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION))
                    .token(UUID.randomUUID().toString()+Instant.now())
                    .build();
            refreshTokenRepository.save(refreshToken);
        }

        return refreshToken;
    }

    @Override
    public RefreshToken validaRefreshToken(RefreshToken token) {
        if(token.getExpirationtime().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new TokenExpiredException("token expired so login again");
        }
        return token;
    }
    @Override
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
}
