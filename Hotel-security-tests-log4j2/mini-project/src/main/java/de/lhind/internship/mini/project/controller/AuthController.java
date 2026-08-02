package de.lhind.internship.mini.project.controller;

import de.lhind.internship.mini.project.dto.request.LoginRequest;
import de.lhind.internship.mini.project.dto.request.RegisterRequest;
import de.lhind.internship.mini.project.dto.response.TokenResponse;
import de.lhind.internship.mini.project.dto.response.UserResponse;
import de.lhind.internship.mini.project.security.CustomUserDetailsService;
import de.lhind.internship.mini.project.security.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Try it:
 * curl -X POST localhost:8080/auth/login \
 *   -H "Content-Type: application/json" \
 *   -d '{"email":"admin@hotel.com","password":"admin123"}'
 *
 * curl localhost:8080/api/hotels -H "Authorization: Bearer <token from above>"
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        // Delegates the actual password check to Spring Security. Throws
        // BadCredentialsException (mapped to 401 in GlobalExceptionHandler)
        // if the email doesn't exist or the password doesn't match.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse created = userDetailsService.register(request);
        return ResponseEntity.ok(created);
    }
}
