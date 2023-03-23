package com.example.blogJovana.request;

import jakarta.validation.constraints.NotNull;

public record CategoryDetailsRequest(@NotNull String title,
                                     @NotNull String description) {
}
