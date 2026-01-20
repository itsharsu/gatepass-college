package com.college.gatepass.repository;

import com.college.gatepass.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByUserEmail(String email);
    Optional<Student> findByUserId(UUID userId);

    boolean existsByEnrollmentNumber(String enrollmentNumber);
}
