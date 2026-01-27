package com.college.gatepass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FacultyCreateResponse {

    private String facultyId;
    private String email;
    private String temporaryPassword;
}
