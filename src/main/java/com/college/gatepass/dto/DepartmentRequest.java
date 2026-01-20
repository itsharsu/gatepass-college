package com.college.gatepass.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {

    @NotBlank(message = "Department Name is Required!")
    private String name;

}
