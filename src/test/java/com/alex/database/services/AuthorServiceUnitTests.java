package com.alex.database.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.alex.database.TestDataUtil;
import com.alex.database.domain.entities.AuthorEntity;
import com.alex.database.repositories.AuthorRepository;
import com.alex.database.services.impl.AuthorServiceImpl;

@SpringBootTest
public class AuthorServiceUnitTests {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    public void testThatFindAll() {

        AuthorEntity authorA = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        AuthorEntity authorB = TestDataUtil.createTestAuthor(2L, "Jess", 40);
        AuthorEntity authorC = TestDataUtil.createTestAuthor(3L, "Dora", 8);
        List<AuthorEntity> authors = new ArrayList<>(Arrays.asList(authorA, authorB, authorC));
        when(authorService.findAll()).thenReturn(authors);

        List<AuthorEntity> result = authorService.findAll();

        assertThat(result).hasSize(3).contains(authorA, authorB, authorC)
                .doesNotContain(TestDataUtil.createTestAuthor(4L, "Haha", 1));
    }

}
