package com.realestate.repository;

import com.realestate.entity.Advert;
import com.realestate.entity.Category;
import com.realestate.entity.enums.AdvertStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByIdAndAdvertsIsNotEmpty(Long id);

    @Query("SELECT a FROM Category a WHERE (:q IS NULL OR Lower(a.title) LIKE %:q% OR Lower(a.description) LIKE %:q%)")
    Page<Category> getAllCategoriesByPage(String q, Pageable pageable);

}
