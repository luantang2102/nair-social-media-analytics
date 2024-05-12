package com.luantang.socialmediaanalytics.exhandler;

import com.luantang.socialmediaanalytics.authentication.exception.*;
import com.luantang.socialmediaanalytics.dashboard.exceptions.AuthNotFoundException;
import com.luantang.socialmediaanalytics.dashboard.exceptions.InvalidAuthorizationCodeException;
import com.luantang.socialmediaanalytics.dashboard.exceptions.NullTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorObject> handleHttpMessageNotReadable(HttpMessageNotReadableException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.BAD_REQUEST);
        errorObject.setMessage("Data cannot be null");
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserNotFound(UserNotFoundException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.NOT_FOUND);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorObject> handleEmailAlreadyExist(EmailAlreadyExistException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.BAD_REQUEST);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorObject> handleBadCredentials(BadCredentialsException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.BAD_REQUEST);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorObject> handleUserDisabled(DisabledException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.BAD_REQUEST);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailConfirmationTokenNotFoundException.class)
    public ResponseEntity<ErrorObject> handleEmailConfirmationTokenNotFound(EmailConfirmationTokenNotFoundException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.NOT_FOUND);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorObject> handleTokenExpired(TokenExpiredException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.UNAUTHORIZED);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(FailedToSendEmailException.class)
    public ResponseEntity<ErrorObject> handleFailedToSendEmail(FailedToSendEmailException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.BAD_REQUEST);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullTokenException.class)
    public ResponseEntity<ErrorObject> handleFailedToSendEmail(NullTokenException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.UNAUTHORIZED);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidAuthorizationCodeException.class)
    public ResponseEntity<ErrorObject> handleHttpClientError(InvalidAuthorizationCodeException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.BAD_REQUEST);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorObject> handleExpiredJwt(ExpiredJwtException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.UNAUTHORIZED);
        errorObject.setMessage("JWT expired");
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorObject> handleSignatureNotMatch(SignatureException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.UNAUTHORIZED);
        errorObject.setMessage("JWT signature does not match locally computed signature");
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthNotFoundException.class)
    public ResponseEntity<ErrorObject> handleAuthNotFound(AuthNotFoundException ex , WebRequest request) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatus(HttpStatus.NOT_FOUND);
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

}
