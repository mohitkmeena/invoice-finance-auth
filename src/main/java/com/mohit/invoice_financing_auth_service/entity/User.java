package com.mohit.invoice_financing_auth_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "user")
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "enter valid email")
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private  String password;
    @Enumerated(EnumType.STRING)
   // @NotBlank(message = "role can't be null choose either company or investor")
    private Role role;

}
