package de.lhind.internship.mini.project.util;

import de.lhind.internship.mini.project.entity.User;
import de.lhind.internship.mini.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seed("admin@hotel.com", "admin123", "ROLE_ADMIN");
        seed("staff@hotel.com", "staff123", "ROLE_STAFF");
    }

    private void seed(String email, String rawPassword, String role) {
        if (userRepository.findByEmail(email).isPresent()) {
            return;
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        userRepository.save(user);
    }
}

