package com.realestate.service;


import com.realestate.entity.TourRequest;
import com.realestate.entity.enums.TourRequestStatus;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.TourRequestMapper;
import com.realestate.payload.request.TourRequestRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.repository.TourRequestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TourRequestsService {

    private final TourRequestsRepository tourRequestsRepository;
    private final TourRequestMapper tourRequestMapper;

    public ResponseMessage<TourRequestResponse> save(TourRequestRequest tourRequestRequest) {

        //DTO-->POJO
       TourRequest tourRequest= tourRequestMapper.mapTourRequestRequestToTourRequest(tourRequestRequest);
       //default status atadık
       tourRequest.setStatus(TourRequestStatus.PENDING);


      TourRequest savedTourRequest= tourRequestsRepository.save(tourRequest);

      return ResponseMessage.<TourRequestResponse>builder()
              .message(SuccessMessages.TOUR_REQUEST_CREATE)
              .httpStatus(HttpStatus.CREATED)
              .object(tourRequestMapper.mapTourRequestToTourRequestResponse(savedTourRequest))
              .build();


    }
}
