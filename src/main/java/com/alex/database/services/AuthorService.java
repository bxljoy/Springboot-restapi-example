package com.alex.database.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.alex.database.domain.entities.AuthorEntity;

@Service
public interface AuthorService {
    
    AuthorEntity createAuthor(AuthorEntity author);

    List<AuthorEntity> findAll();
}
