package com.college.gatepass.service;

import com.college.gatepass.entity.Leave;
import com.college.gatepass.entity.LeaveStatus;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.repository.LeaveRepository;
import com.college.gatepass.security.JwtUtil;
import com.college.gatepass.util.QrGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QrService {

    private final LeaveRepository leaveRepository;
    private final JwtUtil jwtUtil;
    private final QrGenerator qrGenerator;

    public byte[] generateLeaveQr(UUID userId, UUID leaveId) {

        Leave leave = leaveRepository.findByIdAndStudentUserId(leaveId, userId)
                .orElseThrow(() -> new BadRequestException("Leave not found"));

        if (leave.getStatus() != LeaveStatus.APPROVED) {
            throw new BadRequestException("Leave not approved");
        }

        String token = jwtUtil.generateLeaveToken(
                leave.getId(), leave.getStudent().getId()
        );

        return qrGenerator.generateQrImage(token);
    }
}

