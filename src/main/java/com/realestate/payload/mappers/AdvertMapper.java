package com.realestate.payload.mappers;

import com.realestate.entity.*;
import com.realestate.payload.request.AdvertRequest;
import com.realestate.payload.response.AdvertResponse;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AdvertMapper {

    //DTO --> POJO
    public Advert mapToAdvertRequestToAdvert(AdvertRequest advertRequest , List<Image> imageList, Country country , City city, District district,AdvertType advertType, String slug){
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
                .build();
    }

    //POJO--> DTO

    public AdvertResponse mapAdvertToAdvertResponse(Advert advert){
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
                .build();
    }

}