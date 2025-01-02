package com.example.library.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenUtil {

    private static final String tokenSecret = "R07mLKFemnbURK8k5X8DRvBYGrNZjU60IDs5M2QrYD0=";
    private static final long accessTokenExpiration = 15 * 60 * 1000; // 15 minutes
    private static final long refreshTokenExpiration = 7 * 24 * 60 * 60 * 1000; // 7 days

    // Generate Access Token
    public static String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    // Generate Refresh Token
    public static String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("username" , username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public static String refreshAccessToken(String refreshToken) {
        Claims claims = validateToken(refreshToken);
        if (claims == null) {
            throw new RuntimeException("Invalid refresh token");
        }

        // Check if the refresh token is still valid
        if (claims.getExpiration().before(new Date())) {
            throw new RuntimeException("Refresh token has expired");
        }

        // Generate a new access token using the username from the refresh token
        String username = claims.getSubject();
        return generateAccessToken(username);
    }

    private static Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // Extract Username from Token
    public static String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
