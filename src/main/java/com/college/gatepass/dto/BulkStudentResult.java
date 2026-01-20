package com.college.gatepass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkStudentResult {

    private int successCount;
    private int failureCount;
    private List<BulkStudentResponse> created;
    private List<BulkStudentError> errors;
}

