package com.college.gatepass.service.impl;

import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String store(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }


            String originalFilename = file.getOriginalFilename();
            String extension = getExtension(originalFilename);

            String storedFileName =
                    UUID.randomUUID() + extension;

            Path targetLocation =
                    uploadPath.resolve(storedFileName);

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return storedFileName;

        } catch (IOException ex) {
            throw new BadRequestException(
                    "Could not store file. Please try again."
            );
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
