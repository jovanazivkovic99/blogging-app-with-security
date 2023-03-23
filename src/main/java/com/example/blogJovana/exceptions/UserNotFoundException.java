package com.example.blogJovana.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException {

    private HttpStatus status = null;

    private Object data = null;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(
            String message
    ) {
        super(message);
    }

    public UserNotFoundException(
            HttpStatus status,
            String message
    ) {
        this(message);
        this.status = status;
    }

    public UserNotFoundException(
            HttpStatus status,
            String message,
            Object data
    ) {
        this(
                status,
                message
        );
        this.data = data;
    }
}
