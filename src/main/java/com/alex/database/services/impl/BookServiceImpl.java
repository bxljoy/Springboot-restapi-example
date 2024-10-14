package com.alex.database.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.alex.database.domain.entities.BookEntity;
import com.alex.database.repositories.BookRepository;
import com.alex.database.services.BookService;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        Iterable<BookEntity> books = bookRepository.findAll();
        return StreamSupport.stream(books.spliterator(), false).collect(Collectors.toList());
    }
}
