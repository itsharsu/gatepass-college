package com.college.gatepass.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkStudentResponse {
    private String enrollmentNumber;
    private String email;
    private String temporaryPassword;
}
