package com.college.gatepass.controller;

import com.college.gatepass.dto.ApiResponse;
import com.college.gatepass.dto.ChangePasswordRequest;
import com.college.gatepass.service.PasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/change-password")
    public ApiResponse<String> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Authentication auth
    ) {
        UUID userId = UUID.fromString(auth.getName());
        passwordService.changePassword(userId, request);
        return ApiResponse.success("Password changed successfully");
    }
}


