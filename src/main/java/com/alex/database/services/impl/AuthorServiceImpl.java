package com.alex.database.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AuthorEntity save(AuthorEntity author) {
        return authorRepository.save(author);
    }

    @Override
    public List<AuthorEntity> findAll() {
        // return StreamSupport.stream(authorRepository.findAll().spliterator(),
        // false).toList();
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Transactional
    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        return authorRepository.findById(id).map(existtingAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existtingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existtingAuthor::setAge);
            return authorRepository.save(existtingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author does not exist"));
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

}
