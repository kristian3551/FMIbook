package com.example.FMIbook.utils.user;

import jakarta.validation.constraints.Email;
import java.util.UUID;

public class UserDTO {
    private UUID id;

    @Email(message = "email is invalid")
    private String email;

    public UserDTO() {
    }

    public UserDTO(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    public UserDTO(String email) {
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserDTO serializeFromEntity(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(user.getId(), user.getEmail());
    }
}
