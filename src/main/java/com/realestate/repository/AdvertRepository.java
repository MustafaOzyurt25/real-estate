package com.realestate.repository;

import com.realestate.entity.Advert;
import com.realestate.payload.response.AdvertCityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert,Long> {

    Optional<Advert> findBySlug(String slug);

    //@Query("SELECT a.city.name, COUNT(a) FROM Advert a GROUP BY a.city.name")
    //List<Object[]> getAdvertAmountByCity();

    @Query("SELECT new com.realestate.payload.response.AdvertCityResponse(a.city.name, COUNT(a)) FROM Advert a GROUP BY a.city.name")
    List<AdvertCityResponse> getAdvertAmountByCity();


    @Query(value = "SELECT (count(a) = 0) FROM Advert a")
    boolean isEmpty();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Advert a  WHERE a.builtIn = false")
    void deleteAdverts();
}