package com.realestate.service;

import com.realestate.entity.*;
import com.realestate.entity.enums.AdvertStatus;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.helper.PageableHelper;
import com.realestate.payload.mappers.AdvertMapper;
import com.realestate.payload.request.AdvertRequest;
import com.realestate.payload.response.AdvertCityResponse;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final PageableHelper pageableHelper;

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

    public ResponseEntity<Map<String, Object>> getSortedAdvertsByValues(String q, Long categoryId, Long advertTypeId, Double priceStart, Double priceEnd, Integer status, int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort.toLowerCase(),type.toLowerCase());
        AdvertStatus aStatus = null;
        if(status!=null) {
            aStatus = AdvertStatus.getAdvertStatusByNumber(status);
        }
        if(q!=null){
            q=q.trim().toLowerCase().replaceAll("-"," ");
        }
        Page<AdvertResponse> adverts = advertRepository.getSortedAdvertsByValues(q,categoryId,advertTypeId,priceStart,priceEnd,aStatus,pageable)
                .map(advertMapper::mapAdvertToAdvertResponse);
        Map<String, Object> responseBody = new HashMap<>();
        if (adverts.isEmpty()){
            responseBody.put("message", ErrorMessages.CRITERIA_ADVERT_NOT_FOUND);
            return new ResponseEntity<>(responseBody,HttpStatus.OK);
        }
        responseBody.put("Message",SuccessMessages.CRITERIA_ADVERT_FOUND);
        responseBody.put("Adverts",adverts);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

}

