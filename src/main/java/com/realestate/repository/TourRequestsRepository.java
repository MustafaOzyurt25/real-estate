package com.realestate.repository;

import com.realestate.entity.TourRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRequestsRepository extends JpaRepository<TourRequest,Long> {
    int countByAdvertId(Long advertId);
}
