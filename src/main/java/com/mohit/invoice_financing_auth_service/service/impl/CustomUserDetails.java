package com.mohit.invoice_financing_auth_service.service.impl;

import com.mohit.invoice_financing_auth_service.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CustomUserDetails extends User implements UserDetails {
    private String username;
    private String password;
    private final Collection<? extends GrantedAuthority> authorities;
    public CustomUserDetails(User user) {
        this.username=(Objects.nonNull(user.getEmail()))?user.getEmail():user.getPhoneNumber();
        this.password=user.getPassword();
        List<GrantedAuthority>auths=new ArrayList<>();
        auths.add(new SimpleGrantedAuthority(user.getRole().toString()));
        this.authorities=auths;

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

