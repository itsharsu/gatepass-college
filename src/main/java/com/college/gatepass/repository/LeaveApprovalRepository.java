package com.college.gatepass.repository;

import com.college.gatepass.entity.LeaveApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeaveApprovalRepository extends JpaRepository<LeaveApproval, UUID> {
}
