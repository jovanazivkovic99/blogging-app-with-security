package com.example.blogJovana.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryAlreadyExistsException extends RuntimeException {
    private final String title;

    public CategoryAlreadyExistsException(String title) {
        this.title = title;
    }

}
