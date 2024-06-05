package com.example.totalDuration.utils;

import com.example.totalDuration.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
public class TokenValidator {
    private final JwtService jwtService;

    @Autowired
    public TokenValidator(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public boolean invalidToken(String token){
        try {
            if(token == null || !token.startsWith("Bearer ")) {
                return false;
            }

            token = token.substring(7);
            String username = jwtService.extractClaims(token).getSubject();

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if(jwtService.validateToken(token)) {
                    username = jwtService.extractClaims(token).getSubject();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
                            null, null);
                    HttpServletRequest request = ((ServletRequestAttributes) Objects
                            .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
