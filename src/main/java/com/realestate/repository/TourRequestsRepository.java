package com.realestate.repository;

import com.realestate.entity.TourRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TourRequestsRepository extends JpaRepository<TourRequest,Long> {

    @Query(value = "SELECT (count(t) = 0) FROM TourRequest t")
    boolean isEmpty();
}
