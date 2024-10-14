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
import com.alex.database.domain.entities.AuthorEntity;
import com.alex.database.domain.entities.BookEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
    
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Dora", 7);
        BookEntity bookA = TestDataUtil.createTestBook("1234567890", "Java", author);
        String bookJson = objectMapper.writeValueAsString(bookA);
        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + bookA.getIsbn())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedBook() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor(3L, "Dora", 7);
        author.setId(null);
        BookEntity bookA = TestDataUtil.createTestBook("1234567890", "Java", author);
        String bookJson = objectMapper.writeValueAsString(bookA);
        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + bookA.getIsbn())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value("1234567890")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value("Java")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.author.name").value("Dora")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.author.age").value(7)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.author.id").isNumber()
        );
    }
}
