package com.alex.database.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alex.database.TestDataUtil;
import com.alex.database.domain.dto.AuthorDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {
    
    private final MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto(1L, "Alex", 40);
        authorDto.setId(null);
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto(1L, "Alex", 40);
        authorDto.setId(null);
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Alex")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value("40")
        );
    }
}
