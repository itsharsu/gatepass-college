package com.college.gatepass.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String enrollmentNumber;

    @Column(nullable = false)
    private String name;

    private Integer admissionYear;
    private Integer passoutYear;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // getters & setters
}
