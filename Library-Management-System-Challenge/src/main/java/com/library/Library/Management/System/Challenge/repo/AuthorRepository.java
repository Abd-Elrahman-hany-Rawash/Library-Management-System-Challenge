package com.library.Library.Management.System.Challenge.repo;

import com.library.Library.Management.System.Challenge.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value = "SELECT COUNT(*) FROM book_authors WHERE author_id = :authorId", nativeQuery = true)
    long countByAuthorId(Long authorId);
}
