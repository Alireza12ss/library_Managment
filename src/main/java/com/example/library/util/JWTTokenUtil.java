package com.example.library.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JWTTokenUtil {

    private static final String tokenSecret = "R07mLKFemnbURK8k5X8DRvBYGrNZjU60IDs5M2QrYD0=";
    private static final long accessTokenExpiration = 60 * 60 * 1000; // 60 minutes
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
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    // Refresh Access Token
    public static String refreshAccessToken(String refreshToken) {
        Claims claims = validateToken(refreshToken);
        if (claims == null) {
            throw new RuntimeException("Invalid refresh token");
        }

        if (claims.getExpiration().before(new Date())) {
            throw new RuntimeException("Refresh token has expired");
        }

        String username = claims.getSubject();
        return generateAccessToken(username);
    }

    // Validate Token
    public static Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.err.println("JWT has expired: " + e.getMessage());
        } catch (JwtException e) {
            System.err.println("JWT validation error: " + e.getMessage());
        }
        return null;
    }


    // Check if Token is Expired
    public static boolean isTokenExpired(String token) {
        Claims claims = validateToken(token);
        return claims != null && claims.getExpiration().before(new Date());
    }

    // Extract Specific Claim
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = validateToken(token);
        return claims != null ? claimsResolver.apply(claims) : null;
    }

    // Validate Role
    public static boolean validateRole(String token, String requiredRole) {
        Claims claims = validateToken(token);
        if (claims == null) {
            return false;
        }
        String role = claims.get("role", String.class);
        return requiredRole.equals(role);
    }

    // Extract All Claims
    public static Claims getAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            return null;
        }
    }

    public static boolean validateAccessToken(String token) {
        Claims claims = validateToken(token);
        return claims != null && !isTokenExpired(token);
    }


    // Validate Refresh Token
    public static boolean validateRefreshToken(String refreshToken) {
        Claims claims = validateToken(refreshToken);
        return claims != null && !claims.getExpiration().before(new Date());
    }

    // Extract Token from Authorization Header
    public static String extractTokenFromHeader(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    // Extract Username from Token
    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
