package com.jvolima.authandemail.config;

import com.jvolima.authandemail.entities.Role;
import com.jvolima.authandemail.entities.User;
import com.jvolima.authandemail.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Profile("test")
@Configuration
public class IntegrationTestsSeed implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        User user = new User();
        user.setFirstname("Alice");
        user.setLastname("James");
        user.setEmail("alicejames@gmail.com");
        String encodedPassword = passwordEncoder.encode("123456");
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);
        user.setVerificationCode("code");
        user.setEnabled(true);
        userRepository.save(user);
    }
}
