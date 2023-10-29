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
        String jsonBody = objectMapper.writeValueAsString(Factory.seedUserSignUp());
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/sign-up")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void verifyShouldReturnOkWhenCodeIsValid() throws Exception {
        String notVerifiedUserCode = "code2";
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/verify/" + notVerifiedUserCode)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void verifyShouldReturnNotFoundWhenCodeIsInvalid() throws Exception {
        String invalidCode = "invalid";
        ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/verify/" + invalidCode)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
