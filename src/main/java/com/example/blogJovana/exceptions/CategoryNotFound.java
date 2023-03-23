package com.example.blogJovana.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CategoryNotFound extends RuntimeException {
    private HttpStatus status = null;

    private Object data = null;

    public CategoryNotFound() {
        super();
    }

    public CategoryNotFound(
            String message
    ) {
        super(message);
    }

    public CategoryNotFound(
            HttpStatus status,
            String message
    ) {
        this(message);
        this.status = status;
    }

    public CategoryNotFound(
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
