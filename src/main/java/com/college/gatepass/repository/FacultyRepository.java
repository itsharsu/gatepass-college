package com.college.gatepass.repository;

import com.college.gatepass.entity.Department;
import com.college.gatepass.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FacultyRepository extends JpaRepository<Faculty, UUID> {

    Optional<Faculty> findByName(String name);
    Optional<Faculty> findByUserEmail(String email);

}
