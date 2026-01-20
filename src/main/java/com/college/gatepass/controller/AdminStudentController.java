package com.college.gatepass.controller;

import com.college.gatepass.dto.ApiResponse;
import com.college.gatepass.dto.BulkStudentRequest;
import com.college.gatepass.dto.BulkStudentResponse;
import com.college.gatepass.dto.BulkStudentResult;
import com.college.gatepass.entity.BulkMode;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.service.AdminStudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/students")
public class AdminStudentController {

    private final AdminStudentService adminStudentService;

    public AdminStudentController(AdminStudentService adminStudentService) {
        this.adminStudentService = adminStudentService;
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BulkStudentResult>> bulkCreate(
            @RequestParam(defaultValue = "LENIENT") BulkMode mode,
            @Valid @RequestBody List<@Valid BulkStudentRequest> requests
    ) {
        BulkStudentResult result =
                adminStudentService.createStudents(requests, mode);

        if (mode == BulkMode.STRICT && result.getFailureCount() > 0) {
            throw new BadRequestException("Bulk creation failed in STRICT mode");
        }

        return ResponseEntity.ok(
                result.getFailureCount() > 0
                        ? ApiResponse.error(result)
                        : ApiResponse.success(result)
        );
    }

}

