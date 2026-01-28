package com.college.gatepass.service;

import com.college.gatepass.dto.BulkStudentRequest;
import com.college.gatepass.entity.*;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.repository.DepartmentRepository;
import com.college.gatepass.repository.StudentRepository;
import com.college.gatepass.repository.UserRepository;
import com.college.gatepass.util.PasswordGenerator;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentCreationService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String createSingleStudent(BulkStudentRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        Department department = departmentRepository
                .findByName(req.getDepartment())
                .orElseThrow(() ->
                        new BadRequestException(
                                "Invalid department: " + req.getDepartment()
                        )
                );

        String rawPassword = PasswordGenerator.generate();

        User user = new User();
        user.setEmail(req.getEmail());
        user.setMobileNo(req.getMobileNo());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.STUDENT);
        user.setStatus(Status.ACTIVE);
        user.setFirstLogin(true);

        userRepository.save(user);

        Student student = new Student();
        student.setUser(user);
        student.setEnrollmentNumber(req.getEnrollmentNumber());
        student.setName(req.getName());
        student.setAdmissionYear(req.getAdmissionYear());
        student.setPassoutYear(req.getPassoutYear());
        student.setDepartment(department);

        studentRepository.save(student);

        return rawPassword;
    }
}

