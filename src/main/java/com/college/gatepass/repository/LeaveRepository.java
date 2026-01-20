package com.college.gatepass.repository;

import com.college.gatepass.entity.Department;
import com.college.gatepass.entity.Leave;
import com.college.gatepass.entity.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface LeaveRepository extends JpaRepository<Leave, UUID> {
    List<Leave> findByStudentId(UUID studentId);

    List<Leave> findByDepartmentInAndStatus(
            Set<Department> departments,
            LeaveStatus status
    );
}
