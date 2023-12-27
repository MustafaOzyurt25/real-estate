package com.realestate.payload.mappers;

import com.realestate.entity.Advert;
import com.realestate.entity.TourRequest;
import com.realestate.payload.request.TourRequestRequest;
import com.realestate.payload.request.UpdateTourRequestRequest;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.payload.response.UpdateTourRequestResponse;
import com.realestate.service.AdvertService;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class TourRequestMapper {
private final AdvertService advertService;   // DTO-->POJO


    public TourRequest mapTourRequestRequestToTourRequest(TourRequestRequest tourRequestRequest){
        Advert advert= advertService.getAdvertById(tourRequestRequest.getAdvertId());


        return TourRequest.builder()
                .advert(advert)
                .tourDate(tourRequestRequest.getTourDate())
                .tourTime(tourRequestRequest.getTourTime())
                .createAt(LocalDateTime.now())
                .build();
    }

    // POJO-->DTO

    public TourRequestResponse mapTourRequestToTourRequestResponse(TourRequest tourRequest){
        return TourRequestResponse.builder()
                .tourRequestId(tourRequest.getId())//+tourRequestUpdateResponse
                .tourDate(tourRequest.getTourDate())//+
                .tourTime(tourRequest.getTourTime())//+
                .status(tourRequest.getStatus())//+
                .createAt(tourRequest.getCreateAt())
                .updateAt(tourRequest.getUpdateAt())
                .advert(tourRequest.getAdvert())
                .ownerUser(tourRequest.getOwnerUser())
                .guestUser(tourRequest.getGuestUser())
                .build();
    }

    //POJO --> DTO : S06 icin ilgili id li TourRequest guncelleniyor
    public UpdateTourRequestResponse tourRequestUpdateResponse(TourRequest tourRequest){

        return UpdateTourRequestResponse.builder()
                .tourRequestId(tourRequest.getId())
                .tourDate(tourRequest.getTourDate())
                .tourTime(tourRequest.getTourTime())
                .updateAt(LocalDateTime.now()).build();//guncelleme gun ve saati
    }

}
