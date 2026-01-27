package com.college.gatepass.service;

import com.college.gatepass.dto.CreateFacultyRequest;
import com.college.gatepass.dto.FacultyCreateResponse;
import com.college.gatepass.entity.*;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.repository.DepartmentRepository;
import com.college.gatepass.repository.FacultyRepository;
import com.college.gatepass.repository.UserRepository;
import com.college.gatepass.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminFacultyService {

    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public FacultyCreateResponse createFaculty(CreateFacultyRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        // Resolve departments
        Set<Department> departmentSet = new HashSet<>();

        for (String deptName : req.getDepartments()) {
            Department department = departmentRepository
                    .findByName(deptName)
                    .orElseThrow(() ->
                            new BadRequestException("Invalid department: " + deptName)
                    );
            departmentSet.add(department);
        }

        // Generate random password
        String rawPassword = PasswordGenerator.generate();

        // Create User
        User user = new User();
        user.setEmail(req.getEmail());
        user.setMobileNo(req.getMobileNo());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.FACULTY);
        user.setStatus(Status.ACTIVE);
        user.setFirstLogin(true);

        userRepository.save(user);

        // Create Faculty
        Faculty faculty = new Faculty();
        faculty.setUser(user);
        faculty.setName(req.getName());
        faculty.setDepartments(departmentSet);

        facultyRepository.save(faculty);

        // ✅ Return credentials to admin
        return new FacultyCreateResponse(
                faculty.getId().toString(),
                user.getEmail(),
                rawPassword
        );
    }

}
