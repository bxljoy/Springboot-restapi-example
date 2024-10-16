package com.alex.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.alex.database.domain.entities.BookEntity;

@Service
public interface BookService {

    BookEntity createUpdateBook(String isbn, BookEntity bookEntity);

    List<BookEntity> findAll();

    Optional<BookEntity> findOne(String isbn);

    boolean isExists(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity bookEntity);
}
