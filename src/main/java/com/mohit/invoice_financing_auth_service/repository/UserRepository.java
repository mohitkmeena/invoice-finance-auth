package com.mohit.invoice_financing_auth_service.repository;

import com.mohit.invoice_financing_auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
    @Query("SELECT u FROM User u WHERE u.email=:emailOrPhone OR u.phoneNumber=:emailOrPhone")
    Optional<User> findUser(@Param("emailOrPhone") String username);


    Optional<User> findByEmail(String email);
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u = :user")
    void updatePassword(@Param("user") User user, @Param("password") String password);
}
