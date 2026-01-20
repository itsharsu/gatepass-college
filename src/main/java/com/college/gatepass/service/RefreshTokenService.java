package com.college.gatepass.service;

import com.college.gatepass.entity.RefreshToken;
import com.college.gatepass.entity.User;
import com.college.gatepass.exception.UnauthorizedException;
import com.college.gatepass.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private static final long REFRESH_TOKEN_DAYS = 30;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public String createOrReplace(User user) {

        // delete existing token (if any)
        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.flush(); // VERY IMPORTANT

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(generateToken());
        refreshToken.setExpiryDate(
                Instant.now().plus(REFRESH_TOKEN_DAYS, ChronoUnit.DAYS)
        );

        refreshTokenRepository.save(refreshToken);

        return refreshToken.getToken();
    }

    public User validateAndGetUser(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new UnauthorizedException("Refresh token expired");
        }

        return refreshToken.getUser();
    }

    private String generateToken() {
        return UUID.randomUUID().toString()
                + UUID.randomUUID().toString();
    }
}

