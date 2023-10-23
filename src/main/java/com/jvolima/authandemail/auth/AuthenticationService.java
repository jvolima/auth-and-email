package com.jvolima.authandemail.auth;

import com.jvolima.authandemail.config.JwtService;
import com.jvolima.authandemail.exceptions.BadRequestException;
import com.jvolima.authandemail.exceptions.NotFoundException;
import com.jvolima.authandemail.user.Role;
import com.jvolima.authandemail.user.User;
import com.jvolima.authandemail.user.UserRepository;
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

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
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

        return new SignUpResponse(user.getId());
    }

    public SignInResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );
        User user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found."));
        String jwtToken = jwtService.generateToken(user);

        return new SignInResponse(jwtToken);
    }
}
