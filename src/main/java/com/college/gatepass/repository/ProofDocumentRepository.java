package com.college.gatepass.repository;

import com.college.gatepass.entity.ProofDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProofDocumentRepository extends JpaRepository<ProofDocument, UUID> {
}
