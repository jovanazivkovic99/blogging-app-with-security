package com.example.blogJovana.request;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest (@NotBlank String comment, @NotBlank String title){
}
