package com.example.blogJovana.request;

import jakarta.validation.constraints.NotNull;

public record CreatePostRequest(@NotNull String title, @NotNull String content, @NotNull Long categoryId) {
}
