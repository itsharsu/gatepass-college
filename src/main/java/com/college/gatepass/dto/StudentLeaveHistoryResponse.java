package com.college.gatepass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class StudentLeaveHistoryResponse {
    private String leaveId;
    private String reason;
    private String description;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String status;
    private LocalDateTime createdAt;

    private String approvedBy;
    private String remark;

    private List<LeaveDocumentResponse> documents;
}

