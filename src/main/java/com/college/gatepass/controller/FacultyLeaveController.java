package com.college.gatepass.controller;

import com.college.gatepass.dto.FacultyLeaveResponse;
import com.college.gatepass.dto.ApiResponse;
import com.college.gatepass.entity.LeaveStatus;
import com.college.gatepass.service.FacultyLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/faculty/leaves")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('FACULTY','ADMIN')")
public class FacultyLeaveController {

    private final FacultyLeaveService facultyLeaveService;

    @GetMapping
    public ApiResponse<Page<FacultyLeaveResponse>> getLeaves(
            Authentication auth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) LeaveStatus status,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate
    ) {
        UUID userId = UUID.fromString(auth.getName());

        return ApiResponse.success(
                facultyLeaveService.getLeaves(userId, status, fromDate, toDate, page, size)
        );
    }

}
