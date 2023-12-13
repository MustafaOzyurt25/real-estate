package com.realestate.service;

import com.realestate.entity.City;
import com.realestate.entity.District;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;

    private District isDistrictExists(Long id){

        return districtRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.DISTRICT_NOT_FOUND_MESSAGE,id)));

    }
    public District getDistrictById(Long countryId) {
        return isDistrictExists(countryId);
    }
}
