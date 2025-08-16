package com.library.Library.Management.System.Challenge.repo;

import com.library.Library.Management.System.Challenge.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface BookRepository extends JpaRepository<Book, Long> {


}