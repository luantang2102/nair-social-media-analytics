package com.luantang.socialmediaanalytics.authentication.service;

import com.luantang.socialmediaanalytics.authentication.dto.AuthMessageDto;
import com.luantang.socialmediaanalytics.authentication.dto.AuthResponseDto;
import com.luantang.socialmediaanalytics.authentication.dto.LoginDto;
import com.luantang.socialmediaanalytics.authentication.dto.RegisterDto;
import org.springframework.transaction.annotation.Transactional;

public interface AuthenticationService {
    AuthMessageDto register(RegisterDto registerDto);

    @Transactional
    AuthMessageDto confirmToken(String token);

    AuthResponseDto login(LoginDto loginDto);
}
