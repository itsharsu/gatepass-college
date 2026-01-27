package com.college.gatepass.service;

import com.college.gatepass.entity.*;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.repository.LeaveRepository;
import com.college.gatepass.repository.SecurityLogRepository;
import com.college.gatepass.repository.StudentRepository;
import com.college.gatepass.repository.UserRepository;
import com.college.gatepass.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityVerificationService {

    private final JwtUtil jwtUtil;
    private final LeaveRepository leaveRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final SecurityLogRepository securityLogRepository;

    @Transactional
    public void verify(String token, UUID securityUserId) {

        Claims claims = jwtUtil.parseToken(token);

        UUID leaveId = UUID.fromString(claims.get("leaveId", String.class));
        UUID studentId = UUID.fromString(claims.get("studentId", String.class));

        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new BadRequestException("Leave not found"));

        if (leave.getStatus() != LeaveStatus.APPROVED) {
            throw new BadRequestException("Leave not approved");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new BadRequestException("Student not found"));

        User security = userRepository.findById(securityUserId)
                .orElseThrow(() -> new BadRequestException("Security user not found"));

        // Prevent multiple exits
        if (securityLogRepository.existsByLeave(leave)) {
            throw new BadRequestException("Already exited");
        }

        SecurityLog log = new SecurityLog();
        log.setLeave(leave);
        log.setStudent(student);
        log.setVerifiedBy(security);
        log.setExitTime(LocalDateTime.now());

        securityLogRepository.save(log);
    }
}

