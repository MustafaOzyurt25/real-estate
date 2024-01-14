package com.realestate.repository;

import com.realestate.entity.TourRequest;
import com.realestate.entity.enums.TourRequestStatus;
import net.bytebuddy.jar.asm.commons.Remapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TourRequestsRepository extends JpaRepository<TourRequest, Long> {

    // tour request
    @Query(value = "SELECT (count(t) = 0) FROM TourRequest t")
    boolean isEmpty();

    int countByAdvertId(Long advertId);


    List<TourRequest> findByTourDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, TourRequestStatus status);


    boolean existsByGuestUserId(Long userId);

    boolean existsByOwnerUserId(Long userId);


    //S06 updatedTourRequest -----------------------------------------------------------------------------------------*/
    @Query("SELECT CASE WHEN COUNT(tr) > 0 THEN true ELSE false END " +
            "FROM TourRequest tr " +
            "WHERE tr.advert.id = :advertId AND tr.tourDate = :tourDate AND tr.tourTime = :tourTime")
    boolean existsByAdvertIdAndTourDateAndTourTime(
            @Param("advertId") Long advertId,
            @Param("tourDate") LocalDate tourDate,
            @Param("tourTime") LocalTime tourTime
    );


    List<TourRequest> findByGuestUserId(Long guestUserId);


    List<TourRequest> findByOwnerUser_Id(Long userId);

    @Query("SELECT CASE WHEN COUNT(tr) > 0 THEN true ELSE false END FROM TourRequest tr WHERE tr.advert.id = :advertId AND tr.guestUser.id = :id AND tr.status IN (1, 0)")
    boolean existsByAdvertIdAndGuestUserIdAndStatus(@Param("advertId") Long advertId, @Param("id") Long id);
}


/*
    @Query("SELECT COUNT(DISTINCT tr.guestUser) FROM TourRequest tr")
    long countCustomers();   getTourRequestsCount() / reportService icin yazildi ama gerek kalmadi!
*/


