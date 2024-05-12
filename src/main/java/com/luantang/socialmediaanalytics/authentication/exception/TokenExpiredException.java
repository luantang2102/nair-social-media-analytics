package com.luantang.socialmediaanalytics.authentication.exception;

import java.io.Serial;

public class TokenExpiredException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 3;

    public TokenExpiredException(String message) {
        super(message);
    }
}
