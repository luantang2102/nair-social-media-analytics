package com.luantang.socialmediaanalytics.authentication.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthMessageDto {
    private String message;
    private Date timestamp;
}
