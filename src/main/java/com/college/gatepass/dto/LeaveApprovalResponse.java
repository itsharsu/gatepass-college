package com.college.gatepass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LeaveApprovalResponse {
    private String approverName;
    private String status;
    private String remark;
    private LocalDateTime approvedAt;
}