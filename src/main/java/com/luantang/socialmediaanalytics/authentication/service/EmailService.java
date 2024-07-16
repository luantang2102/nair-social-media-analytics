package com.luantang.socialmediaanalytics.authentication.service;

import com.luantang.socialmediaanalytics.authentication.model.EmailConfirmationToken;

public interface EmailService {
    boolean isValidEmail(String email);

    EmailConfirmationToken saveConfirmationToken(EmailConfirmationToken token);

    EmailConfirmationToken getConfirmationToken(String token);

    void sendEmail(String to, String email);
}
