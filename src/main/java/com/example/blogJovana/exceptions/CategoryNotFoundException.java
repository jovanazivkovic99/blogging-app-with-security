package com.example.blogJovana.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryNotFoundException extends RuntimeException {
    private final Long categoryid;

    public CategoryNotFoundException(Long categoryid) {
        this.categoryid = categoryid;
    }
}
