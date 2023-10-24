package com.jvolima.authandemail.services;

import com.jvolima.authandemail.dto.SignInRequestDTO;
import com.jvolima.authandemail.dto.SignInResponseDTO;
import com.jvolima.authandemail.dto.SignUpRequestDTO;
import com.jvolima.authandemail.dto.SignUpResponseDTO;
import com.jvolima.authandemail.exceptions.BadRequestException;
import com.jvolima.authandemail.exceptions.NotFoundException;
import com.jvolima.authandemail.entities.Role;
import com.jvolima.authandemail.entities.User;
import com.jvolima.authandemail.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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
        user = userRepository.save(user);

        return new SignUpResponseDTO(user.getId());
    }

    public SignInResponseDTO signIn(SignInRequestDTO signInRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequestDTO.getEmail(),
                        signInRequestDTO.getPassword()
                )
        );
        User user = userRepository.findByEmail(signInRequestDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found."));
        String jwtToken = jwtService.generateToken(user);

        return new SignInResponseDTO(jwtToken);
    }
}
