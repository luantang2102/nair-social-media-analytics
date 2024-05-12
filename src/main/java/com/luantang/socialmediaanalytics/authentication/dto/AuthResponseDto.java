package com.luantang.socialmediaanalytics.authentication.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";
    private UserDto userDetail;
}
