package com.college.gatepass.repository;

import com.college.gatepass.entity.Department;
import com.college.gatepass.entity.Faculty;
import com.college.gatepass.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface FacultyRepository extends JpaRepository<Faculty, UUID> {

    Optional<Faculty> findByUser(User user);
    Optional<Faculty> findByUserEmail(String email);
    @EntityGraph(attributePaths = "departments")
    Optional<Faculty> findByUserId(UUID userId);


}
