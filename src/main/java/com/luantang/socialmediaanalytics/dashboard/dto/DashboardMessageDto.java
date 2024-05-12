package com.luantang.socialmediaanalytics.dashboard.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardMessageDto {
    private String message;
    private Date timestamp;
}
