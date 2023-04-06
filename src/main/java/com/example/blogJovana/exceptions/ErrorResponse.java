package com.example.blogJovana.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {/*
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;*/

    private String message;
    private HttpStatus httpStatus;

    private LocalDateTime timeStamp;

    public ErrorResponse(String message, HttpStatus httpStatus, LocalDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }
}
