package com.jvolima.authandemail.controllers;

import com.jvolima.authandemail.dto.*;
import com.jvolima.authandemail.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<HelloResponseDTO> hello() {
        HelloResponseDTO helloResponseDTO = new HelloResponseDTO();
        helloResponseDTO.setMessage("Hello authenticated user!");

        return ResponseEntity.ok().body(helloResponseDTO);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        SignUpResponseDTO signUpResponseDTO = userService.signUp(signUpRequestDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(signUpResponseDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/verify")
    public ResponseEntity<VerifyResponseDTO> verify(@RequestParam String code) {
        userService.verifyAccount(code);
        VerifyResponseDTO verifyResponseDTO = new VerifyResponseDTO();
        verifyResponseDTO.setMessage("Account verified successfully.");

        return ResponseEntity.ok().body(verifyResponseDTO);
    }
}
