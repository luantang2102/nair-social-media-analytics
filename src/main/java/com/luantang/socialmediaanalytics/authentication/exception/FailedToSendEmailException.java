package com.luantang.socialmediaanalytics.authentication.exception;

import java.io.Serial;

public class FailedToSendEmailException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 4;

    public FailedToSendEmailException(String message) {
        super(message);
    }
}
