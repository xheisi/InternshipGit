package de.lhind.internship.mini.project.config;

import de.lhind.internship.mini.project.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Stateless JWT authentication for the whole API:
 *  1. POST /auth/login {email, password} -> server returns a signed JWT
 *  2. Client sends it back as "Authorization: Bearer <token>" on every call
 *  3. JwtAuthenticationFilter validates it on each request and rebuilds the
 *     Authentication before the request reaches any controller.
 *
 * No HttpSession is ever created — see SessionCreationPolicy.STATELESS below.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // enables @PreAuthorize on controller/service methods, if you want it later
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // JWTs aren't stored in cookies, so there's no CSRF risk to defend against here
                .csrf(AbstractHttpConfigurer::disable)
                // Never create/use an HttpSession — every request re-proves itself via the token
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Logging in / registering must be reachable without already being logged in
                        .requestMatchers("/auth/**", "/error").permitAll()
                        // Authorization demo: only ADMIN can delete hotels, rooms, or reservations
                        .requestMatchers(HttpMethod.DELETE, "/api/hotels/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/rooms/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/reservations/**").hasRole("ADMIN")
                        // Everything else under /api just needs a valid logged-in user (any role)
                        .anyRequest().authenticated()
                )
                // Insert our token check BEFORE Spring's built-in username/password filter,
                // since we're replacing that flow entirely with token-based auth
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Exposed as a bean so AuthController can call .authenticate(...) to check
    // an email/password pair before handing back a token.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
