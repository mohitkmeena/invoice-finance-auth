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
import com.mohit.invoice_financing_auth_service.service.LoginSignupService;
import com.mohit.invoice_financing_auth_service.service.RefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class LoginSignUpServiceImpl implements LoginSignupService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private JwtServiceImpl jwtService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private RefreshService refreshService;
    @Autowired private UserProducer userProducer;
    @Override
    public ResponseEntity<ResponseDto> signup(SignUpDto signUpDto){
        /*
         * check if user already exists
         * check
         * */
        boolean isExists=ifUserExists(signUpDto);
        if(isExists){
            return new ResponseEntity<>(ResponseDto
                    .builder()
                    .message("user already exist,please try login")
                    .code("ALREADY_EXIST")
                    .build(), HttpStatus.BAD_REQUEST);
        }
        RefreshToken rtoken= refreshService.createRefreshToken(signUpDto.getEmail());
        String token=jwtService.generateToken(signUpDto.getEmail());
        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        User user= User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .phoneNumber(signUpDto.getPhoneNumber())
                .role(Role.valueOf(signUpDto.getRole().toUpperCase()))
                .build();

        User user2= userRepository.save(user);
        if(user2.getRole().toString()=="COMPANY"){
            userProducer.sendCompanytoSave(userToUserEventDto(user2));
        }
        else{
            userProducer.sendInvestortoSave(userToUserEventDto(user2));
        }


        return new ResponseEntity<>(ResponseDto
                .builder()
                .message(token+ "  refresh token "+ rtoken.getToken())
                .code("created successfully")
                .build(),
                HttpStatus.ACCEPTED
        );
    }
    @Override
    public ResponseEntity<ResponseDto> login(LoginDto loginDto){

        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmailOrPhonenUmber(),loginDto.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken rtoken= refreshService.createRefreshToken(loginDto.getEmailOrPhonenUmber());
            String token=jwtService.generateToken(loginDto.getEmailOrPhonenUmber());
            token=jwtService.generateToken(authentication.getName());
            return new ResponseEntity<>(ResponseDto
                    .builder()
                    .message(token+ "  refresh token "+ rtoken.getToken())
                    .code("created successfully")
                    .build(),
                    HttpStatus.ACCEPTED
            );
        }

        else return new ResponseEntity<>(ResponseDto
                .builder()
                .message( " error in token creation, retry")
                .code("NOT_CREATED")
                .build()
                ,HttpStatus.INTERNAL_SERVER_ERROR);
    }




    private boolean ifUserExists(SignUpDto signUpDto){
        boolean checkByemail=false;
        boolean checkByPhone=false;
        if(Objects.nonNull(signUpDto.getEmail())){
            checkByemail=userRepository.existsByEmail(signUpDto.getEmail());
        }
        if(Objects.nonNull(signUpDto.getPhoneNumber())){
            checkByPhone=userRepository.existsByPhoneNumber(signUpDto.getPhoneNumber());
        }
        return checkByemail||checkByPhone;

    }
    @Override
    public   String getUserIdByUsername(String username){
        return userRepository.findByEmail(username).map(User::getEmail).orElse(null);
    }

    public UserEventDto userToUserEventDto(User user){
        return  UserEventDto.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
