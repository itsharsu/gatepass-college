package com.college.gatepass.repository;

import com.college.gatepass.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Optional<Department> findByName(String name);

    boolean existsByNameIgnoreCase(String name);
}
