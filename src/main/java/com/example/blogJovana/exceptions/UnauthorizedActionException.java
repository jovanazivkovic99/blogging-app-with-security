package com.example.blogJovana.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnauthorizedActionException extends RuntimeException {

    private final String email;

    public UnauthorizedActionException(String email) {
        this.email = email;
    }
}
