package com.example.blogJovana.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CommentDetailsRequest (@NotBlank String comment, @NotBlank String title, Long postId){
}
