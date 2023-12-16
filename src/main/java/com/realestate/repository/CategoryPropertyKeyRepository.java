package com.realestate.repository;


import com.realestate.entity.CategoryPropertyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CategoryPropertyKeyRepository extends JpaRepository<CategoryPropertyKey, Long> {
    boolean existsByCategoryId(Long categoryId);

    List<CategoryPropertyKey> findByCategoryId(Long categoryId);

    @Query(value = "SELECT (count(c) = 0) FROM CategoryPropertyKey c ")
    boolean isEmpty();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM CategoryPropertyKey c WHERE c.builtIn = false")
    void deleteCategoryPropertyKeys();
}
