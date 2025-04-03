package com.mohit.invoice_financing_auth_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Table(name = "refreshToken")
public class RefreshToken {
    @Id
    @Column(name="refresht_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;
    @Column(name="token")
    @NotBlank(message = "please enter refresh token value")
    private String token;
    @Column(name="exptime",nullable=false)
    private Instant expirationtime;
    @OneToOne
    @JoinColumn(name="email",referencedColumnName="email")
    private User user;
}
