package com.alex.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.alex.database.domain.entities.BookEntity;

public interface BookRepository
        extends CrudRepository<BookEntity, String>, PagingAndSortingRepository<BookEntity, String> {

    int countByAuthorEntity_Id(Long authorId);

    @Query("SELECT COUNT(b) FROM BookEntity b WHERE b.authorEntity.id = :authorId")
    int getNumberOfBooksByAuthorId(Long authorId);
}
