package com.college.gatepass.service;

import com.college.gatepass.dto.CreateLeaveRequest;
import com.college.gatepass.entity.*;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.repository.LeaveRepository;
import com.college.gatepass.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class StudentLeaveService {

    private final StudentRepository studentRepository;
    private final LeaveRepository leaveRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public void createLeave(
            UUID userId,
            CreateLeaveRequest request,
            List<MultipartFile> documents
    ) {

        Student student = studentRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new BadRequestException("Student not found")
                );

        // 1️⃣ Create Leave
        Leave leave = new Leave();
        leave.setStudent(student);
        leave.setDepartment(student.getDepartment());
        leave.setReason(request.getReason());
        leave.setDescription(request.getDescription());
        leave.setFromDate(request.getFromDate());
        leave.setToDate(request.getToDate());
        leave.setStatus(LeaveStatus.PENDING);
        leave.setCreatedAt(LocalDateTime.now());

        // 2️⃣ Handle optional documents
        if (documents != null && !documents.isEmpty()) {
            for (MultipartFile file : documents) {

                String storedPath =
                        fileStorageService.store(file);

                ProofDocument document = new ProofDocument();
                document.setLeave(leave);
                document.setFilePath(storedPath);
                document.setFileType(file.getContentType());
                document.setUploadedAt(LocalDateTime.now());

                leave.getDocuments().add(document);
            }
        }

        // 3️⃣ Save everything in ONE transaction
        leaveRepository.save(leave);
    }
}
