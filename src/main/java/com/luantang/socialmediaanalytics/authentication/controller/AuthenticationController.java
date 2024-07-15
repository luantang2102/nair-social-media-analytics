package com.luantang.socialmediaanalytics.authentication.controller;

import com.luantang.socialmediaanalytics.authentication.dto.AuthMessageDto;
import com.luantang.socialmediaanalytics.authentication.dto.AuthResponseDto;
import com.luantang.socialmediaanalytics.authentication.dto.LoginDto;
import com.luantang.socialmediaanalytics.authentication.dto.RegisterDto;
import com.luantang.socialmediaanalytics.authentication.repository.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthMessageDto> register(@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(authenticationService.register(registerDto), HttpStatus.CREATED);
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<AuthMessageDto> confirmEmail(@RequestParam("token") String token) {
        return new ResponseEntity<>(authenticationService.confirmToken(token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authenticationService.login(loginDto), HttpStatus.OK);
    }


}
