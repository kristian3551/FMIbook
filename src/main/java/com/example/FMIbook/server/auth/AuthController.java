package com.example.FMIbook.server.auth;

import com.example.FMIbook.domain.auth.AuthService;
import com.example.FMIbook.domain.auth.AuthenticationRequest;
import com.example.FMIbook.domain.auth.AuthenticationResponse;
import com.example.FMIbook.domain.auth.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth/")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        AuthenticationResponse authResponse = authService.refreshToken(request.getHeader("refreshToken"));
        response.setHeader("refreshToken", authResponse.getRefreshToken());
        response.setHeader("accessToken", authResponse.getAccessToken());
    }

    @PostMapping("/register-admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.registerAdmin(request));
    }
}
