package com.realestate.repository;

import com.realestate.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;




public interface CountryRepository extends JpaRepository <Country,Long> {


}
