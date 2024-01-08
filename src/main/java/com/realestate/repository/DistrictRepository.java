package com.realestate.repository;

import com.realestate.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District,Long> {
    List<District> findAllByCityId(Long id);
}
