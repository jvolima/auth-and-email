package com.jvolima.authandemail.services;

import com.jvolima.authandemail.dto.SignUpRequestDTO;
import com.jvolima.authandemail.dto.SignUpResponseDTO;
import com.jvolima.authandemail.entities.Role;
import com.jvolima.authandemail.entities.User;
import com.jvolima.authandemail.exceptions.BadRequestException;
import com.jvolima.authandemail.exceptions.NotFoundException;
import com.jvolima.authandemail.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${spring.api.url}")
    private String apiUrl;

    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequest) {
        Optional<User> userAlreadyExists = userRepository.findByEmail(signUpRequest.getEmail());
        if (userAlreadyExists.isPresent()) {
            throw new BadRequestException("There is already a user with this email.");
        }
        User user = new User();
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setEmail(signUpRequest.getEmail());
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);
        user.setEnabled(false);
        String verificationCode = UUID.randomUUID().toString().replaceAll("-", "");
        user.setVerificationCode(verificationCode);
        user = userRepository.save(user);
        String verificationLink = apiUrl + "/api/v1/users/verify/" + user.getVerificationCode();
        emailService.sendEmail(
                user.getEmail(),
                "Verify account",
                "To verify your account, click on the link: " + verificationLink
        );

        return new SignUpResponseDTO(user.getId());
    }

    public void verifyAccount(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode).orElseThrow(() -> new NotFoundException("User not found."));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
