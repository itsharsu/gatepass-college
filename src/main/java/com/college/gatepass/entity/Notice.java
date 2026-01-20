package com.college.gatepass.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by")
    private User createdBy;

    private LocalDateTime createdAt = LocalDateTime.now();
}
