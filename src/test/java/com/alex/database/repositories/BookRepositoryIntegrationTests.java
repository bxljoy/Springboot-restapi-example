package com.alex.database.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.alex.database.TestDataUtil;
import com.alex.database.domain.entities.AuthorEntity;
import com.alex.database.domain.entities.BookEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Jess", 40);
        BookEntity book = TestDataUtil.createTestBook("1234567890", "Java", author);
        underTest.save(book);
        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultiBooksCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Dora", 7);

        BookEntity bookA = TestDataUtil.createTestBook("1234567890", "Java", author);
        underTest.save(bookA);
        BookEntity bookB = TestDataUtil.createTestBook("0987654321", "Spring", author);
        underTest.save(bookB);
        assertThat(underTest.findAll()).hasSize(2)
                .contains(bookA, bookB)
                .doesNotContain(TestDataUtil.createTestBook("47293749234", "Rust", author));
    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Jess", 40);

        BookEntity book = TestDataUtil.createTestBook("1234567890", "Java", author);
        underTest.save(book);
        book.setTitle("Rust");
        underTest.save(book);
        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent().contains(book);
        assertThat(result.get().getTitle()).isEqualTo("Rust");
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Jess", 40);

        BookEntity book = TestDataUtil.createTestBook("1234567890", "Java", author);
        underTest.save(book);

        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent().contains(book);
        assertThat(result.get()).isEqualTo(book);

        underTest.deleteById(book.getIsbn());
        Optional<BookEntity> resultB = underTest.findById(book.getIsbn());
        assertThat(resultB).isEmpty();
    }

    @Test
    public void testThatCanGetNumberOfBooksByAuthorId() {
        AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Jess", 40);

        BookEntity book1 = TestDataUtil.createTestBook("1", "Java", author);
        underTest.save(book1);
        BookEntity book2 = TestDataUtil.createTestBook("2", "Rust", author);
        underTest.save(book2);
        BookEntity book3 = TestDataUtil.createTestBook("3", "Go", author);
        underTest.save(book3);

        int numberOfBooks = underTest.getNumberOfBooksByAuthorId(author.getId());
        assertThat(numberOfBooks).isEqualTo(3);
    }

    @Test
    public void testThatCountByAuthorId() {
        AuthorEntity author = TestDataUtil.createTestAuthor(1L, "Jess", 40);

        BookEntity book1 = TestDataUtil.createTestBook("1", "Java", author);
        underTest.save(book1);
        BookEntity book2 = TestDataUtil.createTestBook("2", "Rust", author);
        underTest.save(book2);
        BookEntity book3 = TestDataUtil.createTestBook("3", "Go", author);
        underTest.save(book3);

        int numberOfBooks = underTest.countByAuthorEntity_Id(author.getId());
        assertThat(numberOfBooks).isEqualTo(3);
    }
}
