package com.example.totalDuration.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails user);

    Claims extractClaims(String token);

    boolean isValid(String token, UserDetails user);

    public void removeToken(String token);
    public boolean validateToken(String token);
}
