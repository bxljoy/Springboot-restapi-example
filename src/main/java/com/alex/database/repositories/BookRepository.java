package com.alex.database.repositories;

import org.springframework.data.repository.CrudRepository;

import com.alex.database.domain.entities.BookEntity;

public interface BookRepository extends CrudRepository<BookEntity, String> {
    
}
