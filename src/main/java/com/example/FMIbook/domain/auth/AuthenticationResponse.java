package com.example.FMIbook.domain.auth;

import com.example.FMIbook.domain.users.user.UserDTO;

public class AuthenticationResponse {
    private String token;
    private UserDTO user;

    public AuthenticationResponse(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }

    public AuthenticationResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
