package com.jvolima.authandemail.utils;

import com.jvolima.authandemail.dto.SignInRequestDTO;
import com.jvolima.authandemail.dto.SignUpRequestDTO;

public class Factory {

    public static SignUpRequestDTO newUserSignUp() {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setFirstname("John");
        signUpRequestDTO.setLastname("Doe");
        signUpRequestDTO.setEmail("johndoe@gmail.com");
        signUpRequestDTO.setPassword("123456");

        return signUpRequestDTO;
    }

    public static SignUpRequestDTO seedUserSignUp() {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setFirstname("Alice");
        signUpRequestDTO.setLastname("James");
        signUpRequestDTO.setEmail("alicejames@gmail.com");
        signUpRequestDTO.setPassword("123456");

        return signUpRequestDTO;
    }

    public static SignInRequestDTO nonExistingUserSignIn() {
        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setEmail("billysmith@gmail.com");
        signInRequestDTO.setPassword("123456");

        return signInRequestDTO;
    }

    public static SignInRequestDTO seedUserSignIn() {
        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setEmail("alicejames@gmail.com");
        signInRequestDTO.setPassword("123456");

        return signInRequestDTO;
    }
}
