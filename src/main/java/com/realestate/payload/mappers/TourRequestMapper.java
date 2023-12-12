package com.realestate.payload.mappers;

import com.realestate.entity.TourRequest;
import com.realestate.payload.request.TourRequestRequest;
import com.realestate.payload.response.TourRequestResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TourRequestMapper {

   // DTO-->POJO

    public TourRequest mapTourRequestRequestToTourRequest(TourRequestRequest tourRequestRequest){
        return TourRequest.builder()
                .tour_date(tourRequestRequest.getTour_date())
                .tour_time(tourRequestRequest.getTour_time())
                .status(tourRequestRequest.getStatus())
                .create_at(tourRequestRequest.getCreate_at())
                .update_at(tourRequestRequest.getUpdate_at())
                .advert(tourRequestRequest.getAdvertId())
                .build();
    }

    // POJO-->DTO

    public TourRequestResponse mapTourRequestToTourRequestResponse(TourRequest tourRequest){
        return TourRequestResponse.builder()
                .tourRequestId(tourRequest.getId())
                .tour_date(tourRequest.getTour_date())
                .tour_time(tourRequest.getTour_time())
                .status(tourRequest.getStatus())
                .create_at(tourRequest.getCreate_at())
                .update_at(tourRequest.getUpdate_at())
                .build();
    }
}
