package com.realestate.payload.mappers;

import com.realestate.entity.TourRequest;
import com.realestate.payload.request.TourRequestRequest;
import com.realestate.payload.response.TourRequestResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class TourRequestMapper {

   // DTO-->POJO

    public TourRequest mapTourRequestRequestToTourRequest(TourRequestRequest tourRequestRequest){
        return TourRequest.builder()
                .tourDate(tourRequestRequest.getTourDate())
                .tourTime(tourRequestRequest.getTourTime())
                .createAt(LocalDateTime.now())
                .build();
    }

    // POJO-->DTO

    public TourRequestResponse mapTourRequestToTourRequestResponse(TourRequest tourRequest){
        return TourRequestResponse.builder()
                .tourRequestId(tourRequest.getId())
                .tourDate(tourRequest.getTourDate())
                .tourTime(tourRequest.getTourTime())
                .status(tourRequest.getStatus())
                .createAt(tourRequest.getCreateAt())
                .updateAt(tourRequest.getUpdateAt())
                .build();
    }
}
