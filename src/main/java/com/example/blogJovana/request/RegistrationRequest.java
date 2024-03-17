package com.example.blogJovana.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegistrationRequest(@NotNull String firstName, @NotNull String lastName, @NotNull @Email String email,
                                  @NotNull String password) {
}
