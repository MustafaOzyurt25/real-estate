package com.realestate.repository;


import com.realestate.entity.CategoryPropertyKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryPropertyKeyRepository extends JpaRepository<CategoryPropertyKey, Long> {
    boolean existsByCategoryId(Long categoryId);

    List<CategoryPropertyKey> findByCategoryId(Long categoryId);
}
