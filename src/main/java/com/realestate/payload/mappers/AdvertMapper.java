package com.realestate.payload.mappers;

import com.realestate.entity.*;
import com.realestate.payload.request.AdvertRequest;
import com.realestate.payload.request.AdvertUpdateRequest;
import com.realestate.payload.response.AdvertResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdvertMapper {

    //DTO --> POJO

   
    public Advert mapToAdvertRequestToAdvert(AdvertRequest advertRequest , List<Image> imageList, Country country , City city, District district,AdvertType advertType, String slug ,User user,Category category){
        return Advert.builder()
                .title(advertRequest.getTitle())
                .description(advertRequest.getDescription())
                .slug(slug)
                .price(advertRequest.getPrice())
                .location(advertRequest.getLocation())
                .createAt(advertRequest.getCreateAt())   
                .country(country)
                .city(city)
                .district(district)
                .advertType(advertType)
                .images(imageList)
                .category(category)
                .user(user)
                .build();
    }


    //POJO--> DTO


    public AdvertResponse mapAdvertToAdvertResponse(Advert advert) {
        return AdvertResponse.builder()
                .advertId(advert.getId())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .slug(advert.getSlug())
                .price(advert.getPrice())
                .location(advert.getLocation())
                .createAt(advert.getCreateAt())
                .country(advert.getCountry())
                .city(advert.getCity())
                .district(advert.getDistrict())
                .images(advert.getImages())
                .advertStatus(advert.getStatus())
                .isActive(advert.getIsActive())
                .viewCount(advert.getViewCount())
                .updateAt(advert.getUpdateAt())
                .category(advert.getCategory())
                .advertType(advert.getAdvertType())
                .categoryPropertyValues(advert.getCategoryPropertyValue())
                .logAdverts(advert.getLogAdverts())
                .favorites(advert.getFavorites())
                .tourRequests(advert.getTourRequests())
                .built_in(advert.getBuiltIn())
                .user(advert.getUser())
                .build();
    }


    //  DTO --> POJO( update )
    public Advert mapAdvertRequestToUpdatedAdvert(AdvertUpdateRequest advertUpdateRequest) {

        return Advert.builder()
                .title(advertUpdateRequest.getTitle())
                .description(advertUpdateRequest.getDescription())
                .price(advertUpdateRequest.getPrice())
                .location(advertUpdateRequest.getLocation())
                .isActive(advertUpdateRequest.getIsActive())

                
                .build();
    }
    public Advert updateAdminAdvertById(AdvertUpdateRequest advertUpdateRequest) {

        return Advert.builder()
                .title(advertUpdateRequest.getTitle())
                .description(advertUpdateRequest.getDescription())
                .price(advertUpdateRequest.getPrice())
                .location(advertUpdateRequest.getLocation())
                .build();


}

 }