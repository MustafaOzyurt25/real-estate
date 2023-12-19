package com.realestate.repository;

import com.realestate.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByIdAndAdvertsIsNotEmpty(Long id);

    @Query(value = "SELECT (count(c) = 0) FROM Category c")
    boolean isEmpty();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Category c WHERE c.builtIn = false")
    void deleteCategories();
}
