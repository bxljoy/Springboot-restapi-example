package com.alex.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.alex.database.domain.entities.AuthorEntity;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    Iterable<AuthorEntity> ageLessThan(Integer age);

    @Query("SELECT a FROM AuthorEntity a WHERE a.age > ?1")
    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(Integer age);
}
