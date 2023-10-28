package com.jvolima.authandemail.utils;

import com.jvolima.authandemail.dto.SignUpRequestDTO;

public class Factory {

    public static SignUpRequestDTO signUpRequestDTO() {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setFirstname("John");
        signUpRequestDTO.setLastname("Doe");
        signUpRequestDTO.setEmail("johndoe@gmail.com");
        signUpRequestDTO.setPassword("123456");

        return signUpRequestDTO;
    }
}
