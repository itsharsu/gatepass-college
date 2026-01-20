package com.college.gatepass.controller;

import com.college.gatepass.dto.ApiResponse;
import com.college.gatepass.dto.DepartmentRequest;
import com.college.gatepass.dto.DepartmentResponse;
import com.college.gatepass.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/departments")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // 1️⃣ CREATE DEPARTMENT
    @PostMapping
    public ApiResponse<DepartmentResponse> create(
            @Valid @RequestBody DepartmentRequest request) {

        return ApiResponse.success(
                departmentService.createDepartment(request)
        );
    }

    // 3️⃣ LIST
    @GetMapping
    public ApiResponse<List<DepartmentResponse>> getAll() {
        return ApiResponse.success(
                departmentService.getAllDepartments()
        );
    }
}



