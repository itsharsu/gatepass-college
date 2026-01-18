package com.college.gatepass.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;

    private String designation;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
