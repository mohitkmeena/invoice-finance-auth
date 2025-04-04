package com.mohit.invoice_financing_auth_service.repository;

import com.mohit.invoice_financing_auth_service.entity.RefreshToken;

import com.mohit.invoice_financing_auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);

    @Query("SELECT rt FROM RefreshToken rt WHERE rt.user = ?1")
    RefreshToken getByUser(User user);
}
