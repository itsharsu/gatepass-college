package com.college.gatepass.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "leave_id")
    private Leave leave;

    @ManyToOne(optional = false)
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Enumerated(EnumType.STRING)
    private ApproverRole approverRole;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    private String remark;

    private LocalDateTime approvedAt = LocalDateTime.now();

    // getters & setters
}

