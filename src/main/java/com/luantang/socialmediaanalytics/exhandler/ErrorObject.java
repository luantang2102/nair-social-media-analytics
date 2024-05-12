package com.luantang.socialmediaanalytics.exhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorObject {
    private HttpStatus status;
    private String message;
    private Date timestamp;
}
