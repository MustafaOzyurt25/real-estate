package com.realestate.repository;


import com.realestate.entity.AdvertType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertTypeRepository extends JpaRepository <AdvertType, Long > {

    @Query(value = "SELECT (count(a) = 0) FROM AdvertType a")
    boolean isEmpty();

    boolean existsByTitle(String title);
}
