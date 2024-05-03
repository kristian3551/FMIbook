package com.example.FMIbook.domain.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String name;
    @Email(message = "email is invalid")
    private String email;
    @NotEmpty(message = "password not empty")
    private String password;
    @Pattern(regexp = "(student|teacher|admin)", message = "user type is invalid")
    private String type;
    private String facultyNumber;
    private Integer semester;
    private Integer group;
    private String description;
    private String degree;
}
