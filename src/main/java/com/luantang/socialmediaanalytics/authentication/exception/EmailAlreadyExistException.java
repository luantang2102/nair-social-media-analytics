package com.luantang.socialmediaanalytics.authentication.exception;

import java.io.Serial;

public class EmailAlreadyExistException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
