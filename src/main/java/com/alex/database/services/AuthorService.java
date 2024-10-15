package com.alex.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.alex.database.domain.entities.AuthorEntity;

@Service
public interface AuthorService {
    
    AuthorEntity save(AuthorEntity author);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);
}
