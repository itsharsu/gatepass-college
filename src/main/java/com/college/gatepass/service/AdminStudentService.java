package com.college.gatepass.service;

import com.college.gatepass.dto.BulkStudentError;
import com.college.gatepass.dto.BulkStudentRequest;
import com.college.gatepass.dto.BulkStudentResponse;
import com.college.gatepass.dto.BulkStudentResult;
import com.college.gatepass.entity.*;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.repository.DepartmentRepository;
import com.college.gatepass.repository.StudentRepository;
import com.college.gatepass.repository.UserRepository;
import com.college.gatepass.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminStudentService {

    private final StudentCreationService studentCreationService;

    @Transactional
    public BulkStudentResult createStudents(
            List<BulkStudentRequest> requests,
            BulkMode mode
    ) {

        List<BulkStudentResponse> created = new ArrayList<>();
        List<BulkStudentError> errors = new ArrayList<>();

        for (int i = 0; i < requests.size(); i++) {
            BulkStudentRequest req = requests.get(i);

            try {
                // 🔥 Each student runs in its own transaction
                String rawPassword =
                        studentCreationService.createSingleStudent(req);

                created.add(new BulkStudentResponse(
                        req.getEnrollmentNumber(),
                        req.getEmail(),
                        rawPassword
                ));

            } catch (Exception ex) {

                // STRICT mode → rollback everything
                if (mode == BulkMode.STRICT) {
                    throw ex;
                }

                // LENIENT mode → capture error & continue
                errors.add(new BulkStudentError(
                        i,
                        req.getEmail(),
                        ex.getMessage()
                ));
            }
        }

        return new BulkStudentResult(
                created.size(),
                errors.size(),
                created,
                errors
        );
    }
}


