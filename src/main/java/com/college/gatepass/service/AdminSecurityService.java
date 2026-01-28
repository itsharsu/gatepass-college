package com.college.gatepass.service;

import com.college.gatepass.dto.CreateSecurityRequest;
import com.college.gatepass.entity.Role;
import com.college.gatepass.entity.Status;
import com.college.gatepass.entity.User;
import com.college.gatepass.exception.BadRequestException;
import com.college.gatepass.repository.UserRepository;
import com.college.gatepass.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String createSecurity(CreateSecurityRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email already exists");
        }


        String rawPassword = PasswordGenerator.generate();

        User user = new User();
        user.setEmail(req.getEmail());
        user.setMobileNo(req.getMobileNo());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.SECURITY);
        user.setStatus(Status.ACTIVE);
        user.setFirstLogin(true);

        userRepository.save(user);

        // return password so admin can share manually
        return rawPassword;
    }
}

