package com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Root {
    private String access_token;
    private String token_type;
}
