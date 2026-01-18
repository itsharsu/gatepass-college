package com.college.gatepass.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class SecurityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "leave_id")
    private Leave leave;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "verified_by")
    private User verifiedBy; // SECURITY role

    private LocalDateTime exitTime = LocalDateTime.now();

    // getters & setters
}

