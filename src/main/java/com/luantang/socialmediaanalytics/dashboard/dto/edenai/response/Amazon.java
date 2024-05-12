package com.luantang.socialmediaanalytics.dashboard.dto.edenai.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Amazon {
    private String status;
    private String generated_text;
    private Double cost;
}
