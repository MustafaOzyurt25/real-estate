package com.realestate.repository;

import com.realestate.entity.Advert;
import com.realestate.entity.Category;
import com.realestate.entity.enums.AdvertStatus;
import com.realestate.payload.response.CategoryResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.StatisticsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.DoubleStream;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByIdAndAdvertsIsNotEmpty(Long id);


    @Query("SELECT a FROM Category a WHERE (:q IS NULL OR Lower(a.title) LIKE %:q%) AND a.isActive = true")
    Page<Category> getAllCategoriesByPage(@Param("q") String q, Pageable pageable);


    @Query(value = "SELECT (count(c) = 0) FROM Category c")
    boolean isEmpty();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Category c WHERE c.builtIn = false")
    void deleteCategories();


    Category getCategoryById(Long categoryId);

    @Query("SELECT COUNT(DISTINCT c) FROM Category c WHERE c.isActive = true")
    long countPublishedCategories();

    @Query("SELECT c FROM Category c WHERE (:q IS NULL OR LOWER(c.title) LIKE %:q%) ")
    Page<Category> getCategoriesByAdmin(String q, Pageable pageable);
}
