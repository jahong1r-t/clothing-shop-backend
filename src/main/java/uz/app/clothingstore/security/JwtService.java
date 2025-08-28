package uz.app.clothingstore.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-token-expiration}")
    private Long accessExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshExpiration;

    private String generateJwtToken(UserDetails userDetails, Long expiration) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtAccessToken(UserDetails userDetails) {
        return generateJwtToken(userDetails, accessExpiration);
    }

    public String generateJwtRefreshToken(UserDetails userDetails) {
        return generateJwtToken(userDetails, refreshExpiration);
    }

    public String extractUserFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUserFromJwtToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }
}
