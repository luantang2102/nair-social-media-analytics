package com.luantang.socialmediaanalytics.authentication.exception;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 0;

    public UserNotFoundException(String message) {
        super(message);
    }
}
