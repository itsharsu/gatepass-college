package com.college.gatepass.controller;

import com.college.gatepass.dto.ApiResponse;
import com.college.gatepass.dto.LoginRequest;
import com.college.gatepass.dto.LoginResponse;
import com.college.gatepass.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }

    @PostMapping("/refresh")
    public ApiResponse<LoginResponse> refresh(
            @RequestParam String refreshToken) {
        return ApiResponse.success(authService.refresh(refreshToken));
    }
}

