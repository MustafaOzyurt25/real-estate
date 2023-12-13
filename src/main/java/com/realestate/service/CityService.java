package com.realestate.service;

import com.realestate.entity.City;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    private City isCityExists(Long id){

        return cityRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.CITY_NOT_FOUND_MESSAGE,id)));

    }
    public City getCityById(Long countryId) {
        return isCityExists(countryId);
    }
}
