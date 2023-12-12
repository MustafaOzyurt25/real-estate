package com.realestate.repository;

import com.realestate.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepository extends JpaRepository<Advert,Long> {

}