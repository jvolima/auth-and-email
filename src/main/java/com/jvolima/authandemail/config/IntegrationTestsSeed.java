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

        User user1 = new User();
        user1.setFirstname("Alice");
        user1.setLastname("James");
        user1.setEmail("alicejames@gmail.com");
        String encodedPassword1 = passwordEncoder.encode("123456");
        user1.setPassword(encodedPassword1);
        user1.setRole(Role.USER);
        user1.setVerificationCode("code1");
        user1.setEnabled(true);
        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstname("Zoe");
        user2.setLastname("Brown");
        user2.setEmail("zoebrown@gmail.com");
        String encodedPassword2 = passwordEncoder.encode("123456");
        user2.setPassword(encodedPassword2);
        user2.setRole(Role.USER);
        user2.setVerificationCode("code2");
        user2.setEnabled(false);
        userRepository.save(user2);
    }
}
