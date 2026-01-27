package com.college.gatepass.repository;

import com.college.gatepass.entity.Department;
import com.college.gatepass.entity.Leave;
import com.college.gatepass.entity.LeaveStatus;
import com.college.gatepass.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.*;

public interface LeaveRepository extends JpaRepository<Leave, UUID> {
    List<Leave> findByStudentId(UUID studentId);

    @Query("""
    SELECT l FROM Leave l
    LEFT JOIN FETCH l.documents
    LEFT JOIN FETCH l.approvals a
    LEFT JOIN FETCH a.approvedBy
    WHERE l.student = :student
    AND (:status IS NULL OR l.status = :status)
    AND (:from IS NULL OR l.createdAt >= :from)
    AND (:to IS NULL OR l.createdAt <= :to)
    """)
    List<Leave> findStudentLeavesWithFilters(
            Student student,
            LeaveStatus status,
            LocalDateTime from,
            LocalDateTime to
    );

    // PAGINATION VERSION (NO FETCH JOIN!)
    @Query("""
    SELECT l FROM Leave l
    WHERE l.student = :student
    AND (:status IS NULL OR l.status = :status)
    AND (:from IS NULL OR l.createdAt >= :from)
    AND (:to IS NULL OR l.createdAt <= :to)
    """)
    Page<Leave> findStudentLeavesPage(
            Student student,
            LeaveStatus status,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable
    );

    @Query("""
    SELECT l FROM Leave l
    WHERE l.department IN :departments
    AND (:status IS NULL OR l.status = :status)
    AND (:fromDate IS NULL OR l.createdAt >= :fromDate)
    AND (:toDate IS NULL OR l.createdAt <= :toDate)
    """)
    Page<Leave> findFacultyLeavesWithFilters(
            @Param("departments") Set<Department> departments,
            @Param("status") LeaveStatus status,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );

    Optional<Leave> findByIdAndStudentUserId(UUID leaveId, UUID userId);
}
