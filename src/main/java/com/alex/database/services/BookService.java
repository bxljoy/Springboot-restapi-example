package com.alex.database.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alex.database.domain.entities.BookEntity;

@Service
public interface BookService {

    BookEntity createBook(String isbn, BookEntity bookEntity);
    
    List<BookEntity> findAll();
}
