package com.alex.database;

import com.alex.database.domain.dto.AuthorDto;
import com.alex.database.domain.dto.BookDto;
import com.alex.database.domain.entities.AuthorEntity;
import com.alex.database.domain.entities.BookEntity;

public final class TestDataUtil {
    
    private TestDataUtil() {
    }

    public static AuthorEntity createTestAuthor(Long id, String name, Integer age) {
        return AuthorEntity.builder()
            .id(id)
            .name(name)
            .age(age)
            .build();
    }

    public static BookEntity createTestBook(String isbn, String title, final AuthorEntity author) {
        return BookEntity.builder()
            .isbn(isbn)
            .title(title)
            .authorEntity(author)
            .build();
    }

    public static AuthorDto createTestAuthorDto(Long id, String name, Integer age) {
        return AuthorDto.builder()
            .id(id)
            .name(name)
            .age(age)
            .build();
    }

    public static BookDto createTestBookDto(String isbn, String title, final AuthorDto authorDto) {
        return BookDto.builder()
            .isbn(isbn)
            .title(title)
            .author(authorDto)
            .build();
    }
}
