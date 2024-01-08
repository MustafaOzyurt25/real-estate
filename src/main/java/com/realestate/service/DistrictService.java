package com.realestate.service;

import com.realestate.entity.City;
import com.realestate.entity.District;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;

    public ResponseEntity<List<District>> getDistrict() {

        List<District> districtList=districtRepository.findAll();

        return ResponseEntity.ok(districtList);
    }

    private District isDistrictExists(Long id){

        return districtRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.DISTRICT_NOT_FOUND_MESSAGE,id)));

    }
    public District getDistrictById(Long countryId) {
        return isDistrictExists(countryId);
    }


    public ResponseEntity<List<District>> getAllCitiesWithCountryId(Long id) {
        List<District> districts = districtRepository.findAllByCityId(id);
        if (districts.isEmpty()){
            throw new ResourceNotFoundException(String.format(ErrorMessages.CITY_CANNOT_BE_FOUND_BY_COUNTRY_ID,id));
        }
        return ResponseEntity.ok(districts);
    }
}
