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
@ActiveProfiles(profiles = "tests")
public class AuthenticationControllerTests {

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
    public void signInShouldReturnTokenWhenDataIsValid() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(Factory.existingUserSignIn());
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/sign-in")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    @Test
    public void signInShouldReturnUnauthorizedWhenCredentialsAreInvalid() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(Factory.nonExistingUserSignIn());
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/sign-in")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.token").doesNotExist());
    }

    @Test
    public void signInShouldReturnBadRequestWhenAccountIsNotVerified() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(Factory.notVerifiedUserSignIn());
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/sign-in")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.token").doesNotExist());
    }
}
