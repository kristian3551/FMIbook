package com.example.FMIbook.domain.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @Email(message = "email is invalid")
    private String email;
    @NotEmpty(message = "password is empty")
    private String password;
}
