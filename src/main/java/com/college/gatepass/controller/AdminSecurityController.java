package com.college.gatepass.controller;

import com.college.gatepass.dto.ApiResponse;
import com.college.gatepass.dto.CreateSecurityRequest;
import com.college.gatepass.service.AdminSecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/security-users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminSecurityController {

    private final AdminSecurityService adminSecurityService;

    @PostMapping
    public ApiResponse<String> createSecurity(@Valid @RequestBody CreateSecurityRequest req) {
        String password = adminSecurityService.createSecurity(req);
        return ApiResponse.success("Security user created. Temp Password: " + password);
    }
}
