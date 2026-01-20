package com.college.gatepass.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {



        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(nullable = false, unique = true)
        private String name;

}

