package com.realestate.repository;

import com.realestate.entity.Advert;
import com.realestate.payload.response.AdvertCategoriesResponse;
import com.realestate.entity.enums.AdvertStatus;
import com.realestate.payload.response.AdvertCityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    Optional<Advert> findBySlug(String slug);

    @Query("SELECT new com.realestate.payload.response.AdvertCityResponse(a.city.name, COUNT(a)) FROM Advert a GROUP BY a.city.name")
    List<AdvertCityResponse> getAdvertAmountByCity();


    @Query(value = "SELECT (count(a) = 0) FROM Advert a")
    boolean isEmpty();


    @Query("SELECT new com.realestate.payload.response.AdvertCategoriesResponse(a.category.title, COUNT(a)) FROM Advert a GROUP BY a.category.title")
    List<AdvertCategoriesResponse> getAdvertAmountByCategories();

    @Query("SELECT a FROM Advert a WHERE (:q IS NULL OR Lower(a.title) LIKE %:q% OR Lower(a.description) LIKE %:q%) " +
            "AND (:categoryId IS NULL OR a.category.id = :categoryId) " +
            "AND (:advertTypeId IS NULL OR a.advertType.id = :advertTypeId) " +
            "AND (:priceStart IS NULL AND :priceEnd IS NULL OR " +
            "   (a.price BETWEEN :priceStart AND :priceEnd) OR " +
            "   (:priceStart IS NOT NULL AND :priceEnd IS NULL AND a.price >= :priceStart) OR " +
            "   (:priceStart IS NULL AND :priceEnd IS NOT NULL AND a.price <= :priceEnd)) " +
            "AND (:status IS NULL OR a.status = :status)")
    Page<Advert> getSortedAdvertsByValues(String q, Long categoryId, Long advertTypeId, Double priceStart, Double priceEnd, AdvertStatus status, Pageable pageable);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Advert a  WHERE a.builtIn = false")
    void deleteAdverts();


    Page<Advert> findByUserEmail(String email, Pageable pageable);
   // List<Advert> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    /*
    * Aşağıdaki method çalışmadı. NEDEN?
    *
    * User findUserById(Long advertId)
    * */


}