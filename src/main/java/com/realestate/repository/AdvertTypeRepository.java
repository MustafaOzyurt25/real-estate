package com.realestate.repository;


import com.realestate.entity.AdvertType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertTypeRepository extends JpaRepository <AdvertType, Long > {

}
