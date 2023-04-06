package com.example.blogJovana.response;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CommentDetailsResponse (String comment, String title,
                                      LocalDateTime createdAt, Long postId, Long commentId){
}
