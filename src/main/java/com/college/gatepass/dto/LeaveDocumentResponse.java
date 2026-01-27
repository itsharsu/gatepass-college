package com.college.gatepass.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaveDocumentResponse {

    private String documentId;
    private String fileType;
    private String viewUrl;
}
