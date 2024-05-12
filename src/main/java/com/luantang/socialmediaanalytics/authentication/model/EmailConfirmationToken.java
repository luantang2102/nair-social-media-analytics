package com.luantang.socialmediaanalytics.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
public class EmailConfirmationToken {
    @Id
    private String token;
    private Date createdAt;
    private Date expiredAt;
    private Date confirmedAt;
    private String email;
}
