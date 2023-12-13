package com.realestate.service;

import com.realestate.entity.City;

import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public ResponseEntity<List<City>> getCity() {
        List<City> cityList=cityRepository.findAll();
        return ResponseEntity.ok(cityList);
    }

    private City isCityExists(Long id){

        return cityRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.CITY_NOT_FOUND_MESSAGE,id)));

    }
    public City getCityById(Long cityId) {
        return isCityExists(cityId);
    }
}


