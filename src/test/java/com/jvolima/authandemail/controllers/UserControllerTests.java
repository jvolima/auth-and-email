package com.jvolima.authandemail.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jvolima.authandemail.utils.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles(profiles = "test")
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        Mockito.doNothing().when(javaMailSender).send(ArgumentMatchers.any(SimpleMailMessage.class));
    }

    @Test
    public void signUpShouldReturnCreatedWhenDataIsValid() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(Factory.newUserSignUp());
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/sign-up")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void signUpShouldReturnBadRequestWhenEmailIsDuplicated() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(Factory.existingUserSignUp());
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/sign-up")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void verifyShouldReturnOkWhenTokenIsValid() throws Exception {
        String notVerifiedUserCode = "verificationToken2";
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/verify/" + notVerifiedUserCode)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void verifyShouldReturnNotFoundWhenTokenIsInvalid() throws Exception {
        String invalidCode = "invalid";
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/verify/" + invalidCode)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void forgotPasswordShouldReturnOkWhenEmailExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(Factory.existingUserSignIn().getEmail());
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/forgot-password")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void forgotPasswordShouldReturnBadRequestWhenAccountIsNotVerified() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(Factory.notVerifiedUserSignIn().getEmail());
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/forgot-password")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void forgotPasswordShouldReturnNotFoundWhenEmailDoesNotExist() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(Factory.nonExistingUserSignIn().getEmail());
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/forgot-password")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void changePasswordShouldReturnOkWhenTokenIsValid() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(Factory.changePasswordTokenValid());
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/change-password")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void changePasswordShouldReturnOkWhenTokenHasExpired() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(Factory.changePasswordTokenExpired());
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/change-password")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void changePasswordShouldReturnOkWhenTokenIsInvalid() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(Factory.changePasswordTokenInvalid());
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/change-password")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
