package com.realestate.service;

import com.realestate.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
}
