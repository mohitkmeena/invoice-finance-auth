package com.mohit.invoice_financing_auth_service.repository;

import java.util.Optional;

import com.mohit.invoice_financing_auth_service.entity.ForgetPassword;
import com.mohit.invoice_financing_auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword,Integer> {
    @Query("SELECT fp FROM ForgetPassword fp WHERE fp.otp = :otp AND fp.user = :user")
    Optional<ForgetPassword> findByOtpAndUser(@Param("otp") Integer otp, @Param("user") User user);

    ForgetPassword findByUser(User user);
}