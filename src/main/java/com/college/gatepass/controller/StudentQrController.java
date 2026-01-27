package com.college.gatepass.controller;

import com.college.gatepass.service.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/student/qr")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentQrController {

    private final QrService qrService;

    @GetMapping("/leave/{leaveId}")
    public ResponseEntity<byte[]> getLeaveQr(
            @PathVariable UUID leaveId,
            Authentication auth
    ) {
        UUID userId = UUID.fromString(auth.getName());
        byte[] qr = qrService.generateLeaveQr(userId, leaveId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qr);
    }
}

