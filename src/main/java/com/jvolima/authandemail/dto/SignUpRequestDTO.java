package com.jvolima.authandemail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpRequestDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
