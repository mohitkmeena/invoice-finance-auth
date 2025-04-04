package com.mohit.invoice_financing_auth_service.service.impl;

import com.mohit.invoice_financing_auth_service.dto.LoginDto;
import com.mohit.invoice_financing_auth_service.dto.ResponseDto;
import com.mohit.invoice_financing_auth_service.dto.SignUpDto;
import com.mohit.invoice_financing_auth_service.entity.RefreshToken;
import com.mohit.invoice_financing_auth_service.entity.Role;
import com.mohit.invoice_financing_auth_service.entity.User;
import com.mohit.invoice_financing_auth_service.producer.UserEventDto;
import com.mohit.invoice_financing_auth_service.producer.UserProducer;
import com.mohit.invoice_financing_auth_service.repository.UserRepository;
import com.mohit.invoice_financing_auth_service.service.RefreshService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.Objects;

@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findUser(username).orElseThrow(()->new UsernameNotFoundException("user not found with given email or phone number "+username));
        return new CustomUserDetails(user);
    }

}
