package com.college.gatepass.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class CreateFacultyRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Mobile number is required")
    private String mobileNo;

    @NotEmpty(message = "At least one department is required")
    private Set<String> departments;
}

