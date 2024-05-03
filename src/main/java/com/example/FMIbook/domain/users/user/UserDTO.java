package com.example.FMIbook.domain.users.user;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private UUID id;

    @Email(message = "email is invalid")
    private String email;

    public UserDTO(String email) {
        this.email = email;
    }

    public static UserDTO serializeFromEntity(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(user.getId(), user.getEmail());
    }
}
