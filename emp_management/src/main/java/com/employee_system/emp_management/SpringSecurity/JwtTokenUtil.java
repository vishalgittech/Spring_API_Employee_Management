package com.employee_system.emp_management.SpringSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtTokenUtil {

    // Secret key used to sign JWTs (generated securely with HS512 algorithm)
    private SecretKey secretKey;

    // Expiration time for the token, injected from the application properties
    @Value("${jwt.expiration}")
    private long expirationTime; // Expiration time in milliseconds

    // Constructor that generates a secure secret key for HS512 algorithm
    public JwtTokenUtil() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // Generate a JWT token based on the username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)  // Set the username as the subject of the token
                .setIssuedAt(new Date())  // Set the issue date to the current date and time
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // Set expiration time
                .signWith(secretKey)  // Sign the token with the generated secret key
                .compact();  // Build and return the JWT token as a string
    }

    // Extract claims (payload data) from the JWT token
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)  // Set the secret key to validate the token
                .parseClaimsJws(token)  // Parse the JWT token
                .getBody();  // Get the claims (body) of the token
    }

    // Extract the username (subject) from the token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();  // Get the subject of the token (username)
    }

    // Check if the token has expired
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());  // Compare the expiration time with current time
    }

    // Validate the token by checking its username and expiration
    public boolean validateToken(String token, String username) {
        // Token is valid if the username matches and it is not expired
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }
}
