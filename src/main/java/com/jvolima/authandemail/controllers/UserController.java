package com.jvolima.authandemail.controllers;

import com.jvolima.authandemail.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final EmailService emailService;

    @GetMapping("/hello")
    public ResponseEntity<Object> hello() {
        emailService.sendEmail("jvdoparaguai@gmail.com", "email app", "Say hi to my spring boot app.");

        return ResponseEntity.ok().body("Hello authenticated user!");
    }
}
