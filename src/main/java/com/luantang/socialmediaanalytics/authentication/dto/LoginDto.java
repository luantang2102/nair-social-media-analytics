package com.luantang.socialmediaanalytics.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@AllArgsConstructor
@Getter
@Setter
public class LoginDto {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
