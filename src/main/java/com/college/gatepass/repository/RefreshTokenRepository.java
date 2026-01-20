package com.college.gatepass.repository;

import com.college.gatepass.entity.RefreshToken;
import com.college.gatepass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
