package com.realestate.controller;

import com.realestate.entity.Country;

import com.realestate.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;



    @GetMapping
    public ResponseEntity<List<Country>> getCountry(){

        return countryService.getCountry();
    }

}
