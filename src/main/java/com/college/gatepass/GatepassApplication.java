package com.college.gatepass;

import com.college.gatepass.entity.Role;
import com.college.gatepass.entity.Status;
import com.college.gatepass.entity.User;
import com.college.gatepass.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatepassApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatepassApplication.class, args);
    }

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            if (userRepository.count() == 0) {

                User admin = new User();
                admin.setEmail("admin@college.edu");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setMobileNo("7041716751");
                admin.setRole(Role.ADMIN);
                admin.setStatus(Status.ACTIVE);

                userRepository.save(admin);

                System.out.println("✅ Default admin created");
            }
        };
    }
}
