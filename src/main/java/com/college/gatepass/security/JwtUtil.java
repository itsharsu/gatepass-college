package com.college.gatepass.security;

import com.college.gatepass.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET =
            "college_gatepass_super_secure_secret_key_32_chars_long";

    private static final long ACCESS_TOKEN_MINUTES = 15;

    private final Key key = Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8)
    );

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + ACCESS_TOKEN_MINUTES * 60 * 1000)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validate(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

