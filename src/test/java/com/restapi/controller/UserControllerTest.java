package com.restapi.controller;

import com.restapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
    private final String API_URL = "/users";

    @Mock
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userRepository)).build();
    }

    @Test
    public void testInvalidMethod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }
}