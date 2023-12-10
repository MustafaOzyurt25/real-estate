package com.realestate.service;

import com.realestate.entity.Advert;
import com.realestate.entity.Image;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.payload.mappers.AdvertMapper;
import com.realestate.payload.mappers.ImageMapper;
import com.realestate.payload.request.AdvertRequest;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertService {


    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;
    private final ImageService imageService;


    public Advert save(AdvertRequest advertRequest) {
        List<Image> images = imageService.saveAndGetImages(advertRequest.getImages());
        Advert advert = advertMapper.mapToAdvertRequestToAdvert(advertRequest,images);
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
}