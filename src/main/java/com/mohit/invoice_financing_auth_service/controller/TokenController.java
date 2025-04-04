package com.mohit.invoice_financing_auth_service.controller;


import com.mohit.invoice_financing_auth_service.dto.RefreshTokenDto;
import com.mohit.invoice_financing_auth_service.dto.ResponseDto;
import com.mohit.invoice_financing_auth_service.entity.RefreshToken;
import com.mohit.invoice_financing_auth_service.exceptions.TokenNotFoundException;
import com.mohit.invoice_financing_auth_service.service.JwtService;
import com.mohit.invoice_financing_auth_service.service.RefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice/v1/token/")
public class TokenController {
    @Autowired
    private RefreshService refreshTokenService;
    @Autowired
    private JwtService jService;
    @PostMapping("/refreshToken")
    public ResponseDto refreshToken(@RequestBody RefreshTokenDto refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::validaRefreshToken)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jService.generateToken(user.getEmail());
                    return ResponseDto.builder()
                            .message(accessToken+" "+refreshTokenRequestDTO.getToken())
                            .build();
                }).orElseThrow(() ->new TokenNotFoundException("Refresh Token is not in DB..!!"));
    }
}

