package com.library.Library.Management.System.Challenge.repo;

import com.library.Library.Management.System.Challenge.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
