package com.mohit.invoice_financing_auth_service.repository;

import com.mohit.invoice_financing_auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
    @Query("SELECT User u from user Where u.email=:emailOrPhone OR u.phoneNumber:=emailOrPhone")
    Optional<User> findUser(@Param("emailOrPhone") String username);
}
