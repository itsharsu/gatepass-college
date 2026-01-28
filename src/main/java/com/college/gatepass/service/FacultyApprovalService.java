package com.college.gatepass.service;

import com.college.gatepass.dto.LeaveDecisionRequest;
import com.college.gatepass.entity.*;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.repository.FacultyRepository;
import com.college.gatepass.repository.LeaveApprovalRepository;
import com.college.gatepass.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacultyApprovalService {

    private final LeaveRepository leaveRepository;
    private final LeaveApprovalRepository approvalRepository;
    private final FacultyRepository facultyRepository;

    @Transactional
    public void decideLeave(UUID userId, LeaveDecisionRequest req) {

        Faculty faculty = facultyRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("Faculty not found"));

        Leave leave = leaveRepository.findById(req.getLeaveId())
                .orElseThrow(() -> new BadRequestException("Leave not found"));


        if (!faculty.getDepartments().contains(leave.getDepartment())) {
            throw new BadRequestException("Not authorized for this department");
        }

        leave.setStatus(req.getStatus());
        leaveRepository.save(leave);

        LeaveApproval approval = new LeaveApproval();
        approval.setLeave(leave);
        approval.setApprovedBy(faculty.getUser());
        approval.setApproverRole(ApproverRole.FACULTY);
        approval.setStatus(req.getStatus());
        approval.setRemark(req.getRemark());
        approval.setApprovedAt(LocalDateTime.now());

        approvalRepository.save(approval);
    }
}

