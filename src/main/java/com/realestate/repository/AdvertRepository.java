package com.realestate.repository;

import com.realestate.entity.Advert;
import com.realestate.entity.AdvertType;
import com.realestate.entity.Category;
import com.realestate.entity.User;
import com.realestate.entity.enums.TourRequestStatus;
import com.realestate.payload.response.AdvertCategoriesResponse;
import com.realestate.entity.enums.AdvertStatus;
import com.realestate.payload.response.AdvertCityResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface AdvertRepository extends JpaRepository<Advert, Long> {

    Optional<Advert> findBySlug(String slug);

    @Query("SELECT new com.realestate.payload.response.AdvertCityResponse(a.city.name, COUNT(a), a.city.id) FROM Advert a GROUP BY a.city.name, a.city.id")
    List<AdvertCityResponse> getAdvertAmountByCity();


    @Query(value = "SELECT (count(a) = 0) FROM Advert a")
    boolean isEmpty();


    @Query("SELECT new com.realestate.payload.response.AdvertCategoriesResponse(a.category.title, a.category.icon, COUNT(a), a.category.id) FROM Advert a GROUP BY a.category.title, a.category.icon, a.category.id")
    List<AdvertCategoriesResponse> getAdvertAmountByCategories();

    @Query("SELECT a FROM Advert a WHERE (:q IS NULL OR Lower(a.title) LIKE %:q% OR Lower(a.description) LIKE %:q%) " +
            "AND ((:categoryId) IS NULL OR a.category.id IN (:categoryId)) " +
            "AND ((:advertTypeId) IS NULL OR a.advertType.id IN (:advertTypeId)) " +
            "AND (:countryId IS NULL OR a.country.id = :countryId) " +
            "AND (:cityId IS NULL OR a.city.id = :cityId) " +
            "AND (:districtId IS NULL OR a.district.id = :districtId) " +
            "AND (:priceStart IS NULL AND :priceEnd IS NULL OR " +
            "   (a.price BETWEEN :priceStart AND :priceEnd) OR " +
            "   (:priceStart IS NOT NULL AND :priceEnd IS NULL AND a.price >= :priceStart) OR " +
            "   (:priceStart IS NULL AND :priceEnd IS NOT NULL AND a.price <= :priceEnd)) " +
            "AND (:status IS NULL OR a.status = :status)")
    Page<Advert> getSortedAdvertsByValues(String q, List<Long> categoryId, List<Long> advertTypeId, Double priceStart, Double priceEnd, AdvertStatus status,
                                          Long countryId, Long cityId, Long districtId, Pageable pageable);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Advert a  WHERE a.builtIn = false")
    void deleteAdverts();


    Page<Advert> findByUserEmail(String email, Pageable pageable);
   // List<Advert> findByUserId(Long userId);

    boolean existsByUserId(Long userId);


    @Query("SELECT a FROM Advert a WHERE a.createAt >= :date1 " +
            "AND a.createAt <= :date2 " +
            "AND a.category.id = :categoryId " +
            "AND a.advertType.id = :advertTypeId " +
            "AND a.status = :status")
    List<Advert> findAdvertsByParameters(LocalDateTime date1, LocalDateTime date2, Long categoryId, Long advertTypeId, AdvertStatus status);

    @Query("SELECT COUNT(a) FROM Advert a WHERE a.isActive = true")
    long countPublishedAdverts();

    List<Advert> findByUser_Id(Long userId);

    @Query("SELECT a.user FROM Advert a WHERE a.id = :id")
    Optional<User> findByUserWithAdvertId(Long id);

    @Query(value = "SELECT * FROM adverts a ORDER BY (SELECT COUNT(*) FROM tour_requests WHERE a.id = tour_requests.advert_id)  DESC LIMIT :amount" , nativeQuery = true)
    List<Advert> getAdvertsByAmount(int amount);


    /*
    * Aşağıdaki method çalışmadı. NEDEN?
    *
    * User findUserById(Long advertId)
    * */


}