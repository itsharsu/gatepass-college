package com.college.gatepass.service;

import com.college.gatepass.dto.LoginRequest;
import com.college.gatepass.dto.LoginResponse;
import com.college.gatepass.entity.Status;
import com.college.gatepass.entity.User;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.exception.ForbiddenException;
import com.college.gatepass.exception.UnauthorizedException;
import com.college.gatepass.repository.UserRepository;
import com.college.gatepass.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UnauthorizedException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new UnauthorizedException("Invalid email or password");
        }

        if (user.getStatus() != Status.ACTIVE) {
            throw new ForbiddenException(
                    "Account is not active. Please contact administration."
            );
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = refreshTokenService.createOrReplace(user);

        return new LoginResponse(
                accessToken,
                refreshToken,
                user.getRole().name()
        );
    }


    public LoginResponse refresh(String refreshToken) {

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new BadRequestException("Refresh token is required");
        }

        User user = refreshTokenService.validateAndGetUser(refreshToken);

        String newAccessToken = jwtUtil.generateAccessToken(user);

        return new LoginResponse(
                newAccessToken,
                refreshToken,
                user.getRole().name()
        );
    }

}

