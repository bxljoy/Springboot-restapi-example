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
import com.alex.database.domain.entities.AuthorEntity;
import com.alex.database.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

        private final MockMvc mockMvc;

        private final ObjectMapper objectMapper;

        private final AuthorService authorService;

        @Autowired
        public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
                this.mockMvc = mockMvc;
                this.objectMapper = new ObjectMapper();
                this.authorService = authorService;
        }

        @Test
        public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
                AuthorDto authorDto = TestDataUtil.createTestAuthorDto(1L, "Alex", 40);
                authorDto.setId(null);
                String authorJson = objectMapper.writeValueAsString(authorDto);

                mockMvc.perform(
                                MockMvcRequestBuilders.post("/authors")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorJson))
                                .andExpect(
                                                MockMvcResultMatchers.status().isCreated());
        }

        @Test
        public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
                AuthorDto authorDto = TestDataUtil.createTestAuthorDto(1L, "Alex", 40);
                authorDto.setId(null);
                String authorJson = objectMapper.writeValueAsString(authorDto);

                mockMvc.perform(
                                MockMvcRequestBuilders.post("/authors")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorJson))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.id").isNumber())
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.author_name").value("Alex"))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.age").value("40"))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.numberOfBooks").value("100"));
        }

        @Test
        public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/authors")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(
                                                MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
                AuthorEntity authorA = TestDataUtil.createTestAuthor(1L, "Alex", 40);
                AuthorEntity authorB = TestDataUtil.createTestAuthor(2L, "Jess", 40);
                AuthorEntity authorC = TestDataUtil.createTestAuthor(3L, "Dora", 8);
                authorService.save(authorA);
                authorService.save(authorB);
                authorService.save(authorC);
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/authors")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$[0].author_name").value("Alex"))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$[0].age").value(40));
        }

        @Test
        public void testThatGetAuthorReturnsHttpStatus200() throws Exception {
                AuthorEntity authorA = TestDataUtil.createTestAuthor(1L, "Alex", 40);
                authorService.save(authorA);
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/authors/" + authorA.getId())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(
                                                MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testThatGetAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/authors/999")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(
                                                MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        public void testThatGetAuthorReturnsAuthorWhenAuthorExists() throws Exception {
                AuthorEntity authorA = TestDataUtil.createTestAuthor(1L, "Alex", 40);
                authorService.save(authorA);
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/authors/" + authorA.getId())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.id").isNumber())
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.author_name").value("Alex"))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.age").value(40));
        }

        @Test
        public void testThatFullUpdateAuthorReturnsHttpStatus200() throws Exception {
                AuthorEntity authorA = TestDataUtil.createTestAuthor(1L, "Alex", 40);
                authorService.save(authorA);
                AuthorDto authorB = TestDataUtil.createTestAuthorDto(2L, "Jess", 39);
                String authorBJson = objectMapper.writeValueAsString(authorB);

                mockMvc.perform(
                                MockMvcRequestBuilders.put("/authors/" + authorA.getId())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorBJson))
                                .andExpect(
                                                MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testThatFullUpdateAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
                AuthorDto authorB = TestDataUtil.createTestAuthorDto(2L, "Jess", 39);
                String authorBJson = objectMapper.writeValueAsString(authorB);
                mockMvc.perform(
                                MockMvcRequestBuilders.put("/authors/999")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorBJson))
                                .andExpect(
                                                MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        public void testThatFullUpdateAuthorReturnsUpdatedAuthor() throws Exception {
                AuthorEntity authorA = TestDataUtil.createTestAuthor(1L, "Alex", 40);
                authorService.save(authorA);
                AuthorDto authorB = TestDataUtil.createTestAuthorDto(2L, "Jess", 39);
                String authorBJson = objectMapper.writeValueAsString(authorB);

                mockMvc.perform(
                                MockMvcRequestBuilders.put("/authors/" + authorA.getId())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorBJson))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.id").value(authorA.getId()))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.author_name")
                                                                .value(authorB.getName()))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.age").value(authorB.getAge()));
        }

        @Test
        public void testThatPartialUpdateAuthorReturnsHttpStatus200() throws Exception {
                AuthorEntity authorA = TestDataUtil.createTestAuthor(1L, "Alex", 40);
                AuthorEntity savedAuthor = authorService.save(authorA);
                AuthorDto authorB = TestDataUtil.createTestAuthorDto(2L, "Jess", 39);
                String authorBJson = objectMapper.writeValueAsString(authorB);

                mockMvc.perform(
                                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorBJson))
                                .andExpect(
                                                MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testThatPartialUpdateAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
                AuthorDto authorB = TestDataUtil.createTestAuthorDto(2L, "Jess", 39);
                String authorBJson = objectMapper.writeValueAsString(authorB);
                mockMvc.perform(
                                MockMvcRequestBuilders.patch("/authors/999")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorBJson))
                                .andExpect(
                                                MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        public void testThatPartialUpdateAuthorReturnsUpdatedAuthor() throws Exception {
                AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Alex", 40);
                AuthorEntity savedAuthor = authorService.save(author);
                AuthorDto authorDto = TestDataUtil.createTestAuthorDto(2L, "Jess", 39);
                String authorBJson = objectMapper.writeValueAsString(authorDto);

                mockMvc.perform(
                                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorBJson))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId()))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.author_name")
                                                                .value(authorDto.getName()))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge()));
        }

        @Test
        public void testThatDeleteAuthorReturnsHttp204WhenNoExist() throws Exception {
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/authors/9999")
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(
                                                MockMvcResultMatchers.status().isNoContent());
        }

        @Test
        public void testThatDeleteAuthorReturnsHttp204WhenExist() throws Exception {
                AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Alex", 40);
                AuthorEntity savedAuthor = authorService.save(author);
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(
                                                MockMvcResultMatchers.status().isNoContent());
        }
}
