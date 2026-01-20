package com.college.gatepass.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkStudentRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid mobile number"
    )
    private String mobileNo;

    @NotBlank(message = "Enrollment number is required")
    private String enrollmentNumber;

    @NotNull(message = "Admission year is required")
    @Min(value = 2000, message = "Admission year must be valid")
    private Integer admissionYear;

    @NotNull(message = "Passout year is required")
    @Min(value = 2000, message = "Passout year must be valid")
    private Integer passoutYear;

    @NotBlank(message = "Department is required")
    private String department;
}


