package com.college.gatepass.service;

import com.college.gatepass.dto.LeaveApprovalResponse;
import com.college.gatepass.dto.LeaveDocumentResponse;
import com.college.gatepass.dto.StudentLeaveHistoryResponse;
import com.college.gatepass.entity.*;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.repository.LeaveRepository;
import com.college.gatepass.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentLeaveHistoryService {

    private final StudentRepository studentRepository;
    private final LeaveRepository leaveRepository;

    @Transactional(readOnly = true)
    public Page<StudentLeaveHistoryResponse> getLeaveHistory(
            UUID userId,
            LeaveStatus status,
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable
    ) {

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("Student not found"));

        LocalDateTime from = fromDate != null ? fromDate.atStartOfDay() : null;
        LocalDateTime to = toDate != null ? toDate.atTime(23, 59, 59) : null;

        Page<Leave> page = leaveRepository.findStudentLeavesPage(
                student, status, from, to, pageable
        );

        return page.map(leave -> {


            LeaveApproval approval = leave.getApprovals()
                    .stream()
                    .findFirst()
                    .orElse(null);

            String approvedBy = approval != null
                    ? approval.getApprovedBy().getEmail()
                    : null;

            String remark = approval != null ? approval.getRemark() : null;

            List<LeaveDocumentResponse> docs =
                    leave.getDocuments().stream()
                            .map(doc -> new LeaveDocumentResponse(
                                    doc.getId().toString(),
                                    doc.getFileType(),
                                    "/api/v1/files/" + doc.getId()
                            ))
                            .toList();

            return new StudentLeaveHistoryResponse(
                    leave.getId().toString(),
                    leave.getReason(),
                    leave.getDescription(),
                    leave.getFromDate(),
                    leave.getToDate(),
                    leave.getStatus().name(),
                    leave.getCreatedAt(),
                    approvedBy,
                    remark,
                    docs
            );
        });
    }
}
