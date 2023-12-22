package com.realestate.repository;

import com.realestate.entity.TourRequest;
import com.realestate.entity.enums.TourRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TourRequestsRepository extends JpaRepository<TourRequest,Long> {


    // tour request
    @Query(value = "SELECT (count(t) = 0) FROM TourRequest t")
    boolean isEmpty();

    int countByAdvertId(Long advertId);


    List<TourRequest> findByTourDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, TourRequestStatus status);
    

    boolean existsByGuestUserId(Long userId);

    boolean existsByOwnerUserId(Long userId);

}
