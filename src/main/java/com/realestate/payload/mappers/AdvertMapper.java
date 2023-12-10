package com.realestate.payload.mappers;

import com.realestate.entity.Advert;
import com.realestate.entity.Image;
import com.realestate.payload.request.AdvertRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdvertMapper {

    //DTO --> POJO
    public Advert mapToAdvertRequestToAdvert(AdvertRequest advertRequest , List<Image> imageList){
        return Advert.builder()
                .title(advertRequest.getTitle())
                .description(advertRequest.getDescription())
                .slug(advertRequest.getSlug())
                .price(advertRequest.getPrice())
                .location(advertRequest.getLocation())
                .create_at(advertRequest.getCreate_at())
                .images(imageList)
                .build();



    }
}