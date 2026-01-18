package com.college.gatepass.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "leaves")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private String reason;
    private String description;

    private LocalDate fromDate;
    private LocalDate toDate;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status = LeaveStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

}
