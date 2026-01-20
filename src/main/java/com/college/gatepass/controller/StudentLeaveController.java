package com.college.gatepass.controller;

import com.college.gatepass.dto.ApiResponse;
import com.college.gatepass.dto.CreateLeaveRequest;
import com.college.gatepass.service.StudentLeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/student/leaves")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentLeaveController {

    private final StudentLeaveService studentLeaveService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<Void> createLeave(
            @RequestPart("data") CreateLeaveRequest request,
            @RequestPart(value = "documents", required = false)
            List<MultipartFile> documents,
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        studentLeaveService.createLeave(userId, request, documents);
        return ApiResponse.success(null);
    }

}
