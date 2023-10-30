package com.jvolima.authandemail.services;

import com.jvolima.authandemail.dto.*;
import com.jvolima.authandemail.entities.Role;
import com.jvolima.authandemail.entities.User;
import com.jvolima.authandemail.exceptions.BadRequestException;
import com.jvolima.authandemail.exceptions.NotFoundException;
import com.jvolima.authandemail.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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
        String verificationToken = UUID.randomUUID().toString().replaceAll("-", "");
        user.setVerificationToken(verificationToken);
        user = userRepository.save(user);
        String verificationLink = apiUrl + "/api/v1/users/verify/" + user.getVerificationToken();
        emailService.sendEmail(
                user.getEmail(),
                "Verify account",
                "To verify your account, click on the link: " + verificationLink
        );

        return new SignUpResponseDTO(user.getId());
    }

    @Transactional
    public void verifyAccount(String verificationToken) {
        User user = userRepository.findByVerificationToken(verificationToken).orElseThrow(() -> new NotFoundException("User not found."));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        User user = userRepository.findByEmail(forgotPasswordRequestDTO.getEmail()).orElseThrow(() -> new NotFoundException("User not found."));
        String changePasswordToken = UUID.randomUUID().toString().replaceAll("-", "");
        user.setChangePasswordToken(changePasswordToken);
        userRepository.save(user);
        emailService.sendEmail(
                user.getEmail(),
                "Change password",
                "To change your password, use this token: " + changePasswordToken + " and pass it as a parameter in the /change-password route with the new password in the body"
        );
    }

    @Transactional
    public void changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        User user = userRepository.findByChangePasswordToken(changePasswordRequestDTO.getChangePasswordToken()).orElseThrow(() -> new NotFoundException("User not found."));
        String encodedPassword = passwordEncoder.encode(changePasswordRequestDTO.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}
