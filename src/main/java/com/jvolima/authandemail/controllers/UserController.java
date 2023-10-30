package com.jvolima.authandemail.controllers;

import com.jvolima.authandemail.dto.*;
import com.jvolima.authandemail.exceptions.BadRequestException;
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

    @GetMapping("/verify/{verificationToken}")
    public ResponseEntity<VerifyResponseDTO> verify(@PathVariable String verificationToken) {
        userService.verifyAccount(verificationToken);
        VerifyResponseDTO verifyResponseDTO = new VerifyResponseDTO();
        verifyResponseDTO.setMessage("Account verified successfully.");

        return ResponseEntity.ok().body(verifyResponseDTO);
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponseDTO> forgotPassword(@RequestBody ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        userService.forgotPassword(forgotPasswordRequestDTO);
        ForgotPasswordResponseDTO forgotPasswordResponseDTO = new ForgotPasswordResponseDTO();
        forgotPasswordResponseDTO.setMessage("Email to change password has been sent");

        return ResponseEntity.ok().body(forgotPasswordResponseDTO);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ChangePasswordResponseDTO> changePassword(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {
        userService.changePassword(changePasswordRequestDTO);
        ChangePasswordResponseDTO changePasswordResponseDTO = new ChangePasswordResponseDTO();
        changePasswordResponseDTO.setMessage("Password changed successfully");

        return ResponseEntity.ok().body(changePasswordResponseDTO);
    }
}
