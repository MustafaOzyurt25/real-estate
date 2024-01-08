package com.realestate.repository;

import com.realestate.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CityRepository extends JpaRepository<City,Long> {
    List<City> findAllByCountryId(Long id);
}
