package com.college.gatepass.service;


import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String store(MultipartFile file);
}

