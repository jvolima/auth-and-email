package com.jvolima.authandemail.utils;

import com.jvolima.authandemail.dto.ChangePasswordRequestDTO;
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

    public static SignUpRequestDTO existingUserSignUp() {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setFirstname("Alice");
        signUpRequestDTO.setLastname("James");
        signUpRequestDTO.setEmail("alicejames@gmail.com");
        signUpRequestDTO.setPassword("123456");

        return signUpRequestDTO;
    }

    public static SignInRequestDTO existingUserSignIn() {
        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setEmail("alicejames@gmail.com");
        signInRequestDTO.setPassword("123456");

        return signInRequestDTO;
    }

    public static SignInRequestDTO notVerifiedUserSignIn() {
        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setEmail("zoebrown@gmail.com");
        signInRequestDTO.setPassword("123456");

        return signInRequestDTO;
    }

    public static SignInRequestDTO nonExistingUserSignIn() {
        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setEmail("billysmith@gmail.com");
        signInRequestDTO.setPassword("123456");

        return signInRequestDTO;
    }

    public static ChangePasswordRequestDTO changePasswordTokenValid() {
        ChangePasswordRequestDTO changePasswordRequestDTO = new ChangePasswordRequestDTO();
        changePasswordRequestDTO.setChangePasswordToken("validChangePasswordToken");
        changePasswordRequestDTO.setNewPassword("654321");

        return changePasswordRequestDTO;
    }

    public static ChangePasswordRequestDTO changePasswordTokenExpired() {
        ChangePasswordRequestDTO changePasswordRequestDTO = new ChangePasswordRequestDTO();
        changePasswordRequestDTO.setChangePasswordToken("expiredChangePasswordToken");
        changePasswordRequestDTO.setNewPassword("654321");

        return changePasswordRequestDTO;
    }

    public static ChangePasswordRequestDTO changePasswordTokenInvalid() {
        ChangePasswordRequestDTO changePasswordRequestDTO = new ChangePasswordRequestDTO();
        changePasswordRequestDTO.setChangePasswordToken("invalidChangePasswordToken");
        changePasswordRequestDTO.setNewPassword("654321");

        return changePasswordRequestDTO;
    }
}
