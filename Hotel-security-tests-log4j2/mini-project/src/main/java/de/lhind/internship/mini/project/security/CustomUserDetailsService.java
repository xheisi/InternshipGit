package de.lhind.internship.mini.project.security;

import de.lhind.internship.mini.project.dto.request.RegisterRequest;
import de.lhind.internship.mini.project.dto.response.UserResponse;
import de.lhind.internship.mini.project.entity.User;
import de.lhind.internship.mini.project.exception.DuplicateResourceException;
import de.lhind.internship.mini.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Two jobs:
 *  1. Implements Spring Security's UserDetailsService — this is what gets
 *     called automatically during login to fetch "who is this user".
 *  2. Handles registration (plain application logic, nothing Spring-Security-specific).
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email " + email));
        return new AppUserPrincipal(user);
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password())); // never store plain text

        String role = (request.role() == null || request.role().isBlank()) ? "ROLE_STAFF" : request.role();
        user.setRole(role);

        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getEmail(), saved.getRole());
    }
}
