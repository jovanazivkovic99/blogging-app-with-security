package com.example.blogJovana.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthenticationRequest(@Email String email, @NotNull String password) {
}
