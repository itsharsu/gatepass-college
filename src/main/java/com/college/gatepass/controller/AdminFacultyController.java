package com.college.gatepass.controller;

import com.college.gatepass.dto.ApiResponse;
import com.college.gatepass.dto.CreateFacultyRequest;
import com.college.gatepass.dto.FacultyCreateResponse;
import com.college.gatepass.service.AdminFacultyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/faculty")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminFacultyController {

    private final AdminFacultyService adminFacultyService;

    @PostMapping
    public ApiResponse<FacultyCreateResponse> createFaculty(
            @Valid @RequestBody CreateFacultyRequest request
    ) {
        return ApiResponse.success(adminFacultyService.createFaculty(request));
    }

}
