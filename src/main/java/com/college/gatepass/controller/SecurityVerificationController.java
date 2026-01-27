package com.college.gatepass.controller;

import com.college.gatepass.dto.ApiResponse;
import com.college.gatepass.service.SecurityVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/security/verify")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SECURITY')")
public class SecurityVerificationController {

    private final SecurityVerificationService verificationService;

    @PostMapping
    public ApiResponse<String> verifyQr(@RequestBody String qrToken,
                                        Authentication auth) {
        UUID securityUserId = UUID.fromString(auth.getName());
        verificationService.verify(qrToken, securityUserId);
        return ApiResponse.success("Student allowed to exit");
    }
}

