package com.realestate.repository;

import com.realestate.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CityRepository extends JpaRepository<City,Long> {
}
