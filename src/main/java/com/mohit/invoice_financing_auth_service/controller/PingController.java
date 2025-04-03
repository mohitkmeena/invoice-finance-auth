package com.mohit.invoice_financing_auth_service.controller;

import com.mohit.invoice_financing_auth_service.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth/v1/ping/")
public class PingController {
    @Autowired
    private UserDetailsImpl userDetailsService;
    @GetMapping("/")
    public ResponseEntity<String> ping(@RequestHeader("Authorization") String authHeader){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.isAuthenticated()){
            String userId=userDetailsService.getUserIdByUsername(authentication.getName());
            if(Objects.nonNull(userId)){
                return ResponseEntity.ok()
                        .header("X-User-ID", userId)
                        .header("Authorization", authHeader)
                        .body("authenticated");
            }
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");

    }
}
