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
import com.alex.database.domain.dto.BookDto;
import com.alex.database.domain.entities.AuthorEntity;
import com.alex.database.domain.entities.BookEntity;
import com.alex.database.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
    
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorDto author = TestDataUtil.createTestAuthorDto(1L, "Dora", 7);
        BookDto bookA = TestDataUtil.createTestBookDto("1234567890", "Java", author);
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
        AuthorDto author = TestDataUtil.createTestAuthorDto(3L, "Dora", 7);
        author.setId(null);
        BookDto bookA = TestDataUtil.createTestBookDto("1234567890", "Java", author);
        String bookJson = objectMapper.writeValueAsString(bookA);
        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + bookA.getIsbn())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value("1234567890")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value("Java")
        );
    }


    @Test
    public void testThatListBooksReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksReturnsListOfBooks() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor(3L, "Dora", 7);
        author.setId(null);
        BookEntity bookA = TestDataUtil.createTestBook("1234567890", "Java", author);
        bookService.createBook(bookA.getIsbn(), bookA);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/books")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].isbn").value("1234567890")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].title").value("Java")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].author.name").value("Dora")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].author.age").value("7")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].author.id").isNumber()
        )
        ;
    }

    @Test
    public void testThatGetBookReturnsHttpStatus200() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        BookEntity book = TestDataUtil.createTestBook("123456789", "Kotlin", author);
        bookService.createBook(book.getIsbn(),book);
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/" + book.getIsbn())
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBookReturnsHttpStatus404WhenNoBookExists() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/999")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetBookReturnsBookWhenBookExists() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        BookEntity book = TestDataUtil.createTestBook("123456789", "Kotlin", author);
        bookService.createBook(book.getIsbn(),book);
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/" + book.getIsbn())
                    .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value("123456789")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value("Kotlin")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.author.age").value(40)
        )
        ;
    }
}
