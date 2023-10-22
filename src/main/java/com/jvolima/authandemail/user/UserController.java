package com.jvolima.authandemail.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/hello")
    public ResponseEntity<Object> hello() {
        return ResponseEntity.ok().body("Hello authenticated user!");
    }
}
