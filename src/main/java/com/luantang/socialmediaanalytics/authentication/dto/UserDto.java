package com.luantang.socialmediaanalytics.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private UUID userId;
    private String firstname;
    private String lastname;
    private String email;
    private int inspireTokens;
}
