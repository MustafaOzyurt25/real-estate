package com.realestate.service;

import com.realestate.entity.*;
import com.realestate.entity.enums.AdvertStatus;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.AdvertMapper;
import com.realestate.payload.request.AdvertRequest;
import com.realestate.payload.response.AdvertCategoriesResponse;
import com.realestate.payload.response.AdvertCityResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertService {


    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;
    private final ImageService imageService;
    private final CountryService countryService;
    private final CityService cityService;
    private final DistrictService districtService;
    private final AdvertTypeService advertTypeService;

    public Advert save(AdvertRequest advertRequest) {

        Country country = countryService.getCountyById(advertRequest.getCountryId());
        City city = cityService.getCityById(advertRequest.getCityId());
        District district = districtService.getDistrictById(advertRequest.getDistrictId());
        AdvertType advertType = advertTypeService.getAdvertTypeById(advertRequest.getAdvertTypeId());

        String slug = advertRequest.getTitle().toLowerCase().replaceAll("\\s", "-").replaceAll("[^a-z0-9-]", "");

        List<Image> images = imageService.saveAndGetImages(advertRequest.getImages());
        Advert advert = advertMapper.mapToAdvertRequestToAdvert(advertRequest,images,country,city,district,advertType,slug);

        advert.setStatus(AdvertStatus.PENDING);
        advert.setBuiltIn(false);
        advert.setIsActive(true);
        advert.setViewCount(0);

        advertRepository.save(advert);
        return advert;
    }

    public void addImageToAdvert(Long advertId, List<Image> images) {
        Advert advert = advertRepository.findById(advertId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages
                        .ADVERT_NOT_FOUND_EXCEPTION,advertId)));
        images.addAll(advert.getImages());
        Advert updatedAdvert = advert.toBuilder()
                .images(images)
                .build();
        advertRepository.save(updatedAdvert);
    }



    public ResponseMessage<Advert> getAdvertWithSlug(String slug) {

         Advert advert = getAdvertBySlug(slug);

         return ResponseMessage.<Advert>builder()
                 .object(advert)
                 .message(SuccessMessages.ADVERT_FOUNDED)
                 .httpStatus(HttpStatus.OK)
                 .build();

    }

    public Advert getAdvertBySlug(String slug){
        return advertRepository.findBySlug(slug).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_NOT_FOUND_EXCEPTION_BY_SLUG,slug)));
    }


    public List<AdvertCityResponse> getAdvertAmountByCity() {

        return advertRepository.getAdvertAmountByCity().stream().collect(Collectors.toList());
    }

    public List<AdvertCategoriesResponse> getAdvertAmountByCategories() {
        return advertRepository.getAdvertAmountByCategories().stream().collect(Collectors.toList());
    }
}

