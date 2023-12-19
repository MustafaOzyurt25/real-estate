package com.realestate.service;


import com.realestate.entity.Advert;
import com.realestate.entity.TourRequest;
import com.realestate.entity.enums.TourRequestStatus;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.TourRequestMapper;
import com.realestate.payload.request.TourRequestRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.repository.TourRequestsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TourRequestsService {

    private final TourRequestsRepository tourRequestsRepository;
    private final TourRequestMapper tourRequestMapper;
    private final AdvertService advertService;

    public ResponseMessage<TourRequestResponse> save(TourRequestRequest tourRequestRequest){

      // //url den slug çekmek için

      // HttpPost request=new HttpPost("http://localhost:8080/adverts/");

      // CloseableHttpClient httpClient= HttpClients.createDefault();

      // try {

      //     CloseableHttpResponse  response = httpClient.execute(request);
      //     response.close();
      //     httpClient.close();
      // } catch (IOException e) {
      //     throw new RuntimeException(e);
      // }

      // String advertUrl=request.getURI().toString();
      // String slug= advertUrl.substring(31);


      // //Slug ile advert çektik
      // Advert advert= advertService.getAdvertBySlug(slug);
      // tourRequestRequest.setAdvertId(advert.getId());





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

    public ResponseMessage<TourRequestResponse> delete(Long id) {
        TourRequest tourRequest = tourRequestsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND,id)));
        tourRequestsRepository.deleteById(id);
        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.mapTourRequestToTourRequestResponse(tourRequest))
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.TOUR_REQUEST_DELETED)
                .build();
    }
    public ResponseMessage<TourRequestResponse> getTourRequestById(Long tourRequestId) {
        TourRequest getTourRequest = tourRequestsRepository.findById(tourRequestId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND,tourRequestId)));
        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.mapTourRequestToTourRequestResponse(getTourRequest))
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.RETURNED_TOUR_REQUEST_DETAILS)
                .build();
    }
    /*

     */
}
