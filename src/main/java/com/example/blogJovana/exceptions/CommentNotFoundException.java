package com.example.blogJovana.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentNotFoundException extends RuntimeException{
    private final Long commentId;

    public CommentNotFoundException(Long commentId) {
        this.commentId = commentId;
    }
}
