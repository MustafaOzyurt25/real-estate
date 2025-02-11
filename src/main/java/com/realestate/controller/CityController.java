package com.realestate.controller;

import com.realestate.entity.City;
import com.realestate.entity.Country;
import com.realestate.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    //U02
    @GetMapping
    public ResponseEntity<List<City>> getCity(){
        return cityService.getCity();
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<City>> getAllCitiesWithCountryId(@PathVariable("id")Long id){
        return cityService.getAllCitiesWithCountryId(id);
    }
}
