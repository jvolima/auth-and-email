package com.jvolima.authandemail.services;

import com.jvolima.authandemail.dto.SignInRequestDTO;
import com.jvolima.authandemail.dto.SignInResponseDTO;
import com.jvolima.authandemail.exceptions.BadRequestException;
import com.jvolima.authandemail.exceptions.NotFoundException;
import com.jvolima.authandemail.entities.User;
import com.jvolima.authandemail.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional(readOnly = true)
    public SignInResponseDTO signIn(SignInRequestDTO signInRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequestDTO.getEmail(),
                        signInRequestDTO.getPassword()
                )
        );
        User user = userRepository.findByEmail(signInRequestDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found."));
        if (!user.getEnabled()) {
            throw new BadRequestException("Account not verified, check your email.");
        }
        String jwtToken = jwtService.generateToken(user);

        return new SignInResponseDTO(jwtToken);
    }
}
