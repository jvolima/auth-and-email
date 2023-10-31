package com.jvolima.authandemail.config;

import com.jvolima.authandemail.entities.Role;
import com.jvolima.authandemail.entities.User;
import com.jvolima.authandemail.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

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
        user1.setVerificationToken("code1");
        user1.setEnabled(true);
        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstname("Zoe");
        user2.setLastname("Brown");
        user2.setEmail("zoebrown@gmail.com");
        String encodedPassword2 = passwordEncoder.encode("123456");
        user2.setPassword(encodedPassword2);
        user2.setRole(Role.USER);
        user2.setVerificationToken("code2");
        user2.setEnabled(false);
        userRepository.save(user2);

        User user3 = new User();
        user3.setFirstname("Thomas");
        user3.setLastname("Jefferson");
        user3.setEmail("thomasjefferson@gmail.com");
        String encodedPassword3 = passwordEncoder.encode("123456");
        user3.setPassword(encodedPassword3);
        user3.setRole(Role.USER);
        user3.setVerificationToken("code3");
        user3.setEnabled(true);
        user3.setChangePasswordToken("validToken");
        user3.setChangePasswordTokenExpirationDate(Instant.now());
        userRepository.save(user3);

        User user4 = new User();
        user4.setFirstname("Serena");
        user4.setLastname("Williams");
        user4.setEmail("serenawilliams@gmail.com");
        String encodedPassword4 = passwordEncoder.encode("123456");
        user4.setPassword(encodedPassword4);
        user4.setRole(Role.USER);
        user4.setVerificationToken("code4");
        user4.setEnabled(true);
        user4.setChangePasswordToken("invalidToken");
        user4.setChangePasswordTokenExpirationDate(Instant.now().minus(1, ChronoUnit.DAYS));
        userRepository.save(user4);
    }
}
