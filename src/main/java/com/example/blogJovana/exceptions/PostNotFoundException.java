package com.example.blogJovana.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostNotFoundException extends RuntimeException {
    private final Long postId;

    public PostNotFoundException(Long postId) {
        this.postId = postId;
    }
}
