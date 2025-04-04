package com.mohit.invoice_financing_auth_service.service.impl;

import com.mohit.invoice_financing_auth_service.dto.EmailDto;
import com.mohit.invoice_financing_auth_service.entity.ForgetPassword;
import com.mohit.invoice_financing_auth_service.entity.User;
import com.mohit.invoice_financing_auth_service.exceptions.ForgetPasswordReqException;
import com.mohit.invoice_financing_auth_service.exceptions.GlobalExceptionHandler;
import com.mohit.invoice_financing_auth_service.exceptions.UserNotFoundException;
import com.mohit.invoice_financing_auth_service.repository.ForgetPasswordRepository;
import com.mohit.invoice_financing_auth_service.repository.UserRepository;
import com.mohit.invoice_financing_auth_service.service.ForgetPasswordService;
import com.mohit.invoice_financing_auth_service.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {
    @Autowired
    private UserRepository userRepository;
    @Autowired private MailService emailService;
    @Autowired private ForgetPasswordRepository forgetPasswordRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public String verifyEmailAndSendOtp(String email){
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("enter correct email"));
        Integer otp=otpGeneration();
        EmailDto mailBody=EmailDto.builder()
                .to(email)
                .sub("otp verification")
                .message("otp to reset the password "+otp)
                .build();

        ForgetPassword forgetPassword=ForgetPassword.builder()
                .user(user)
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis()+10*60*1000))
                .build();
        emailService.sendMail(mailBody);

        forgetPasswordRepository.save(forgetPassword);
        return "email sent successfully";
    }

    private Integer otpGeneration(){
        Random random=new Random();
        return random.nextInt(000_000, 999_999);
    }

    public String verifyOtp(Integer otp,String email){
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("user not found"));
        ForgetPassword forgetPassword=forgetPasswordRepository.findByOtpAndUser(otp, user).orElseThrow(()-> new ForgetPasswordReqException("request not found"));
        if(forgetPassword.getExpirationTime().before(Date.from(Instant.now()))){

            forgetPasswordRepository.delete(forgetPassword);
            return "Otp expired";
        }
        return "OTP VERIFIED";
    }
    @Transactional
    public String updatePassword(String email,String password,String rpassword){
        if(!Objects.equals(password, rpassword)){
            return"password not matching";
        }
        password=passwordEncoder.encode(rpassword);

        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("user not found"));
        userRepository.updatePassword(user, password);
        ForgetPassword forgetPassword=forgetPasswordRepository.findByUser(user);
        forgetPasswordRepository.delete(forgetPassword);
        return "updated successful";
    }
}
