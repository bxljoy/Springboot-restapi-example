package com.alex.database.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.alex.database.TestDataUtil;
import com.alex.database.domain.entities.AuthorEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private final AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        underTest.save(author);
        Optional<AuthorEntity> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultiAuthorsCanBeCreatedAndRecalled() {
        AuthorEntity authorA = TestDataUtil.createTestAuthor(1L, "Jess", 40);
        underTest.save(authorA);
        AuthorEntity authorB = TestDataUtil.createTestAuthor(2L, "John", 50);
        underTest.save(authorB);
        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result).hasSize(2)
                .contains(authorA, authorB);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        underTest.save(author);
        author.setName("Jess");
        underTest.save(author);
        Optional<AuthorEntity> result = underTest.findById(author.getId());
        assertThat(result).isPresent().contains(author);
        assertThat(result.get().getName()).isEqualTo("Jess");
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        underTest.save(author);

        Optional<AuthorEntity> result = underTest.findById(author.getId());
        assertThat(result).isPresent().contains(author);
        assertThat(result.get()).isEqualTo(author);

        underTest.deleteById(author.getId());

        Optional<AuthorEntity> resultB = underTest.findById(author.getId());
        assertThat(resultB).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan() {
        AuthorEntity authorA = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        AuthorEntity authorB = TestDataUtil.createTestAuthor(2L, "Jess", 40);
        AuthorEntity authorC = TestDataUtil.createTestAuthor(3L, "Dora", 8);

        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        Iterable<AuthorEntity> results = underTest.ageLessThan(40);

        assertThat(results).hasSize(1).contains(authorC);

    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        AuthorEntity authorA = TestDataUtil.createTestAuthor(1L, "Alex", 40);
        AuthorEntity authorB = TestDataUtil.createTestAuthor(2L, "Jess", 40);
        AuthorEntity authorC = TestDataUtil.createTestAuthor(3L, "Dora", 8);

        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        Iterable<AuthorEntity> results = underTest.findAuthorsWithAgeGreaterThan(10);

        assertThat(results).hasSize(2).contains(authorA, authorB);
    }
}
