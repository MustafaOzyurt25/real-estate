package com.realestate.payload.mappers;

import com.realestate.entity.*;
import com.realestate.payload.request.AdvertRequest;
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
                .create_at(advertRequest.getCreate_at())
                .country(country)
                .city(city)
                .district(district)
                .advertType(advertType)
                .images(imageList)
                .build();


    }

}