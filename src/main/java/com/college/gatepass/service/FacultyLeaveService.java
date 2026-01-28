package com.college.gatepass.service;

import com.college.gatepass.dto.FacultyLeaveResponse;
import com.college.gatepass.dto.LeaveDocumentResponse;
import com.college.gatepass.entity.Department;
import com.college.gatepass.entity.Faculty;
import com.college.gatepass.entity.Leave;
import com.college.gatepass.entity.LeaveStatus;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.repository.FacultyRepository;
import com.college.gatepass.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacultyLeaveService {

    private final LeaveRepository leaveRepository;
    private final FacultyRepository facultyRepository;


    @Transactional(readOnly = true)
    public Page<FacultyLeaveResponse> getLeaves(
            UUID userId,
            LeaveStatus status,
            LocalDate fromDate,
            LocalDate toDate,
            int page,
            int size
    ) {

        Faculty faculty = facultyRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("Faculty not found"));

        Set<Department> departments = faculty.getDepartments();

        LocalDateTime from = fromDate != null ? fromDate.atStartOfDay() : null;
        LocalDateTime to = toDate != null ? toDate.atTime(23,59,59) : null;

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Leave> leavePage = leaveRepository.findFacultyLeavesWithFilters(
                departments, status, from, to, pageable
        );

        return leavePage.map(leave -> new FacultyLeaveResponse(
                leave.getId().toString(),
                leave.getStudent().getName(),
                leave.getStudent().getEnrollmentNumber(),
                leave.getDepartment().getName(),
                leave.getReason(),
                leave.getDescription(),
                leave.getFromDate(),
                leave.getToDate(),
                leave.getStatus().name(),
                leave.getDocuments().stream()
                        .map(doc -> new LeaveDocumentResponse(
                                doc.getId().toString(),
                                doc.getFileType(),
                                "/api/v1/files/" + doc.getId()
                        ))
                        .toList()
        ));
    }

}
