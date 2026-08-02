package de.lhind.internship.mini.project.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

/**
 * Builds and verifies JSON Web Tokens.
 * A JWT has 3 parts (header.payload.signature). We don't handle these
 * strings manually — the jjwt library builds/parses them for us:
 *  - Header:    algorithm info, added automatically by signWith(...)
 *  - Payload:   subject = user's email, "roles" claim, issuedAt, expiration
 *  - Signature: HMAC-SHA256 over header+payload using our secret key.
 *               This is what makes the token tamper-proof: change one
 *               character of the payload and the signature no longer matches.
 */
@Service
public class JwtService {

    // Base64-encoded secret key, set in application.properties. Never commit
    // a real one to source control — this is a demo/training value only.
    @Value("${jwt.secret:}")
    private String secret;

    @Value("${jwt.expiration-ms:3600000}") // default: 1 hour
    private long expirationMs;

    public String generateToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .subject(userDetails.getUsername())
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey())
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        // This throws JwtException automatically if the signature is invalid,
        // the token is malformed, or it's expired — we don't check those by hand.
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
