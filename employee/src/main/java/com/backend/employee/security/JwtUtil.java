package com.backend.employee.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds
	private static final String SECRET_KEY = "kM3q4TbN8Gf9CWPc6rXLvTpyHZmF7xNCzKJQzTQ69no=";

	private static SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
	}

	// Generate JWT token
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();

		// Get roles from authorities
		List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

		// Create claims
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", roles);

		return Jwts.builder().claims(claims) // Add custom claims
				.subject(username) // Set the subject (username)
				.issuedAt(new Date()) // Set issue date
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration date
				.signWith(getSigningKey()) // FIX: Removed MacAlgorithm parameter
				.compact();
	}

	// Extract claims from token
	public Claims extractClaims(String token) {
		return Jwts.parser() // Use the new parser method
				.verifyWith(getSigningKey()) // Corrected method for signature verification
				.build().parseSignedClaims(token) // New method for parsing signed claims
				.getPayload(); // Retrieve the claims
	}

	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	// Method to validate JWT (optional)
	public boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

	// Method to check if token is expired
	private boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
	}

}
