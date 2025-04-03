package com.mohit.invoice_financing_auth_service.service.impl;

import com.mohit.invoice_financing_auth_service.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${app.jwt-secret}")
    private String SECRET_KEY;

    @Value("${app.jwt-expiration}")
    private Long EXPIRATION;

    @Override
    public String generateToken(String userName) {
        return generateToken(new HashMap<>(),userName);
    }
    public String generateToken(Map<String,Object> extraclaims, String username){
            extraclaims=new HashMap<>(extraclaims);
            return Jwts.builder()
                    .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                    .setSubject(username)
                    .setClaims(extraclaims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .signWith(key(), SignatureAlgorithm.HS256)
                    .compact();
    }

    private Key key() {
        byte [] keys= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keys);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    public <T>T extractClaims(String token, Function<Claims,T>claimsResolver){
        Claims claims=extractAllclaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllclaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Date expirationTime(String token){
          return extractClaims(token,Claims::getExpiration);
    }
    private boolean isTokenExpired(String token){
        return expirationTime(token).before(new Date(System.currentTimeMillis()));
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=extractUsername(token);
        return username.equals(userDetails.getUsername())&&!isTokenExpired(token);
    }
}
