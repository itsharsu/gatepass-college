package com.college.gatepass.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private String reason;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "leave",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProofDocument> documents = new ArrayList<>();
}

