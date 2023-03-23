package com.example.blogJovana.response;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CategoryDetailsResponse(@NotNull String title,
                                      @NotNull String description,
                                      LocalDateTime createdAt) {
}
