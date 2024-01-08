package com.realestate.controller;

import com.realestate.entity.City;
import com.realestate.entity.Country;
import com.realestate.entity.District;
import com.realestate.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    //U03
    @GetMapping
    public ResponseEntity<List<District>> getDistrict(){
        return districtService.getDistrict();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<District>> getAllDistrictsWithCityId(@PathVariable("id")Long id){
        return districtService.getAllCitiesWithCountryId(id);
    }

}
