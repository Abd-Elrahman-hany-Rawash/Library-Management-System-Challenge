package com.library.Library.Management.System.Challenge.repo;

import com.library.Library.Management.System.Challenge.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
