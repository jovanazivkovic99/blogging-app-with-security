package com.example.blogJovana.service.impl;

import com.example.blogJovana.repository.TokenBlacklistRepository;
import com.example.blogJovana.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY = "72357538782F413F442A472D4B6150645367566B597033733676397924422645";
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    public String extractEmail(String jwt) {
        return extractClaim(purifyToken(jwt), Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            claims.put("role", authorities.iterator().next().getAuthority());
        }
        return generateToken(claims, userDetails);
    }

    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractEmail(purifyToken(token));
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(purifyToken(token)) && !isTokenBlacklisted(purifyToken(token));
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(purifyToken(token), Claims::getExpiration);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JwtServiceImpl.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String purifyToken(String withPrefix) {
        String result = withPrefix.trim();
        if (result.startsWith("Bearer ")) {
            result = result.substring("Bearer ".length());
        }
        return result.trim();
    }

    private Boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.existsByToken(token);
    }
}
