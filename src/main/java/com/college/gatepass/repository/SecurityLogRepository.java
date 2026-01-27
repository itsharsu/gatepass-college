package com.college.gatepass.repository;

import com.college.gatepass.entity.Leave;
import com.college.gatepass.entity.SecurityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SecurityLogRepository extends JpaRepository<SecurityLog, UUID> {
    boolean existsByLeave(Leave leave);
}
