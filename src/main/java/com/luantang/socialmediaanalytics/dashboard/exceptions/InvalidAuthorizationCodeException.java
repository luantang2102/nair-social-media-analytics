package com.luantang.socialmediaanalytics.dashboard.exceptions;

public class InvalidAuthorizationCodeException extends RuntimeException{
    public InvalidAuthorizationCodeException(String message) {
        super(message);
    }
}
