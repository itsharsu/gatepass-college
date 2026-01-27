package com.college.gatepass.controller;

import com.college.gatepass.dto.ApiResponse;
import com.college.gatepass.dto.LeaveDecisionRequest;
import com.college.gatepass.service.FacultyApprovalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/faculty/leaves")
@RequiredArgsConstructor
@PreAuthorize("hasRole('FACULTY')")
public class FacultyApprovalController {

    private final FacultyApprovalService approvalService;

    @PostMapping("/decision")
    public ApiResponse<Void> decideLeave(
            @RequestBody @Valid LeaveDecisionRequest request,
            Authentication auth
    ) {
        UUID userId = UUID.fromString(auth.getName());
        approvalService.decideLeave(userId, request);
        return ApiResponse.success(null);
    }
}
