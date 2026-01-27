package com.college.gatepass.dto;

import com.college.gatepass.entity.LeaveStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;


@Data
public class LeaveDecisionRequest {

    @NotNull
    private UUID leaveId;

    @NotNull
    private LeaveStatus status; // APPROVED or REJECTED

    private String remark;
}
