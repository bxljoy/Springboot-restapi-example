package com.alex.database.services.impl;

import org.springframework.stereotype.Service;

import com.alex.database.domain.entities.AuthorEntity;
import com.alex.database.repositories.AuthorRepository;
import com.alex.database.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity author) {
        return authorRepository.save(author);
    }
    
}
