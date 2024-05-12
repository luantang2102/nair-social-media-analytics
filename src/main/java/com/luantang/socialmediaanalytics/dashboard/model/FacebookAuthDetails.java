package com.luantang.socialmediaanalytics.dashboard.model;

import com.luantang.socialmediaanalytics.dashboard.dto.facebook.response.user.account.Root;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Document
public class FacebookAuthDetails {
    @Id
    private String facebookUserId;
    @Indexed(unique = true)
    private String appUserEmail;
    private String userAccessToken;
    private Date expiration;
    private Root pages;

}
