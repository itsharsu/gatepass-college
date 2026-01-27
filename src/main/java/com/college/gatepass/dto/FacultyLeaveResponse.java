package com.college.gatepass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class FacultyLeaveResponse {

    private String leaveId;

    private String studentName;
    private String enrollmentNumber;
    private String department;

    private String reason;
    private String description;

    private LocalDate fromDate;
    private LocalDate toDate;

    private String status;

    private List<LeaveDocumentResponse> documents;
}
