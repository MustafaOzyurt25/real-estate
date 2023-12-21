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


    public ResponseMessage<TourRequestResponse> save(TourRequestRequest tourRequestRequest) {

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
        TourRequest tourRequest = tourRequestMapper.mapTourRequestRequestToTourRequest(tourRequestRequest);


        //default status atadık
        tourRequest.setStatus(TourRequestStatus.PENDING);
        TourRequest savedTourRequest = tourRequestsRepository.save(tourRequest);

        return ResponseMessage.<TourRequestResponse>builder()
                .message(SuccessMessages.TOUR_REQUEST_CREATE)
                .httpStatus(HttpStatus.CREATED)
                .object(tourRequestMapper.mapTourRequestToTourRequestResponse(savedTourRequest))
                .build();


    }

    public ResponseMessage<TourRequestResponse> delete(Long id) {
        TourRequest tourRequest = tourRequestsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND, id)));
        tourRequestsRepository.deleteById(id);
        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.mapTourRequestToTourRequestResponse(tourRequest))
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.TOUR_REQUEST_DELETED)
                .build();
    }

    public ResponseMessage<TourRequestResponse> getTourRequestById(Long tourRequestId) {
        TourRequest getTourRequest = tourRequestsRepository.findById(tourRequestId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND, tourRequestId)));
        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.mapTourRequestToTourRequestResponse(getTourRequest))
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.RETURNED_TOUR_REQUEST_DETAILS)
                .build();
    }

    /*

     */

    /**
     //S06 put   //It will update a tour request -> tur talebini guncelle ----------------------------------------------

     //1--It will return the updated tour request object/    - Güncellenmis tur istegini nesnesini dondurecektir.

     //2-Only the tour requests whose status pending or rejected can be updated./  -Yalnızca beklemede/pending veya reddedilmiş/rejected/DECLINED durumu olan tur talepleri güncellenebilir.
     --------------------------------------------------------------//pending veya rejected olup olmadigini kontrol et!!!
     //3-If a request is updated, the status field should reset to “pending” / -Bir istek güncellenirse durum alanı "beklemede/pending" olarak sıfırlanmalıdır
     ---------------------------------------------------//gonderilen tourRequest guncellenirse pending olarak sifirla!!!


     public static ResponseMessage<TourRequestResponse> updatedTourRequest(TourRequest tourRequest, Long tourRequestId) {
     //!!! id kontrol ---> pending veya reddedilmis tur taleplerini guncelle. update edilecek tourrequest var mi?

     TourRequest tourRequest1 = isTourRequestExist(tourRequestId);

     //-----------------------------------------------------------------------------------------------------------------

    public ResponseMessage<TourRequestResponse> updatedTourRequest(Long tourRequestId, TourRequestRequest TourRequestRequest) {
        TourRequest tourRequest = tourRequestsRepository.findById(tourRequestId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND, tourRequestId)));

        // Sadece "pending" veya "rejected/DECLINED" durumundaki istekleri güncelle -> REJECTED dokumantasyonda yazan, projede yazan DECLINED!!!
        if (tourRequest.getStatus() == TourRequestStatus.PENDING || tourRequest.getStatus() == TourRequestStatus.DECLINED) {
            tourRequest.setTourDate(TourRequestRequest.getTourDate());
            tourRequest.setTourTime(TourRequestRequest.getTourTime());
           // tourRequest.setTourId(TourRequestRequest.getTourId()); ---> TODO advert_id, tour_id ?????????????????????????

            //gonderilen tourRequest guncellenirse pending olarak sifirla!!!
            tourRequest.setStatus(TourRequestStatus.PENDING);


            TourRequest updatedTourRequest = tourRequestsRepository.save(tourRequest);
            return ResponseMessage.<TourRequestResponse>builder()
                    .message(SuccessMessages.TOUR_REQUEST_UPDATED)
                    .httpStatus(HttpStatus.OK)
                    .object(tourRequestMapper.mapTourRequestToTourRequestResponse(updatedTourRequest))
                    .build();
        } else {
            return ResponseMessage.<TourRequestResponse>builder()
                    .message(ErrorMessages.TOUR_REQUEST_CANNOT_BE_UPDATED)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

*/


}
