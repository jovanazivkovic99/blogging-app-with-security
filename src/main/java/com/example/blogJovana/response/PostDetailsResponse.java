package com.example.blogJovana.response;

import java.time.LocalDateTime;

public record PostDetailsResponse(String title, String content, LocalDateTime createdAt) {
}
