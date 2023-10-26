package com.jvolima.authandemail.controllers;

import com.jvolima.authandemail.dto.*;
import com.jvolima.authandemail.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO signInRequestDTO) {
        SignInResponseDTO signInResponseDTO = authenticationService.signIn(signInRequestDTO);

        return ResponseEntity.ok().body(signInResponseDTO);
    }
}
