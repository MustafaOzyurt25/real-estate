package com.realestate.service;

import com.realestate.entity.Country;
import com.realestate.entity.Role;
import com.realestate.entity.enums.RoleType;
import com.realestate.exception.ResourceNotFoundException;

import com.realestate.messages.ErrorMessages;
import com.realestate.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;


    public ResponseEntity<List<Country>> getCountry() {
        List<Country> countryList=countryRepository.findAll();
        return ResponseEntity.ok(countryList);
    }

    private Country isCountryExists(Long id){

        return countryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.COUNTRY_NOT_FOUND_MESSAGE,id)));

    }
    public Country getCounrtyById(Long countryId) {
        return isCountryExists(countryId);
    }
}
