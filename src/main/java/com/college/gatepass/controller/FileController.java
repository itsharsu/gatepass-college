package com.college.gatepass.controller;

import com.college.gatepass.entity.ProofDocument;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.repository.ProofDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final ProofDocumentRepository proofDocumentRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/{documentId}")
    @PreAuthorize("hasAnyRole('FACULTY','ADMIN','SECURITY')")
    public ResponseEntity<Resource> viewFile(@PathVariable UUID documentId) {

        ProofDocument document = proofDocumentRepository.findById(documentId)
                .orElseThrow(() ->
                        new BadRequestException("Document not found")
                );

        Path filePath = Paths.get(uploadDir)
                .resolve(document.getFilePath())
                .normalize();

        if (!Files.exists(filePath)) {
            throw new BadRequestException("File not found on server");
        }

        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new BadRequestException("Invalid file path");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(resource);
    }
}
