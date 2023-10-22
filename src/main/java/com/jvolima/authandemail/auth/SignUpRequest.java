package com.jvolima.authandemail.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
