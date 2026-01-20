package com.college.gatepass.service;

import com.college.gatepass.dto.DepartmentRequest;
import com.college.gatepass.dto.DepartmentResponse;
import com.college.gatepass.entity.Department;
import com.college.gatepass.entity.Faculty;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.repository.DepartmentRepository;
import com.college.gatepass.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;

    public DepartmentResponse createDepartment(DepartmentRequest request) {

        String deptName = request.getName().trim().toUpperCase();

        if (departmentRepository.existsByNameIgnoreCase(deptName)) {
            throw new BadRequestException(
                    "Department already exists: " + deptName
            );
        }

        Department department = new Department();
        department.setName(deptName);

        Department saved = departmentRepository.save(department);

        return new DepartmentResponse(
                saved.getId().toString(),
                saved.getName()
        );
    }



    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(d ->
                        new DepartmentResponse(
                                d.getId().toString(),
                                d.getName()
                        )
                )
                .toList();
    }
}

