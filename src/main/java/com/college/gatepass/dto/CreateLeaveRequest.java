package com.college.gatepass.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import jakarta.validation.constraints.*;

@Data
public class CreateLeaveRequest {

    @NotNull(message = "From date is required")
    private LocalDate fromDate;

    @NotNull(message = "To date is required")
    private LocalDate toDate;

    @NotBlank(message = "Reason is required")
    private String reason;

    private String description;

    @AssertTrue(message = "To date must be after or equal to from date")
    public boolean isDateValid() {
        return toDate == null || fromDate == null || !toDate.isBefore(fromDate);
    }
}

