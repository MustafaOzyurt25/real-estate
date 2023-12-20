package com.realestate.service;


import com.realestate.entity.Advert;
import com.realestate.entity.TourRequest;
import com.realestate.entity.User;
import com.realestate.entity.enums.TourRequestStatus;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.helper.PageableHelper;
import com.realestate.payload.mappers.TourRequestMapper;
import com.realestate.payload.request.TourRequestRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.repository.TourRequestsRepository;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TourRequestsService {

    private final TourRequestsRepository tourRequestsRepository;
    private final TourRequestMapper tourRequestMapper;
    private final AdvertService advertService;
    private final UserRepository userRepository;
    private final PageableHelper pageableHelper;

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

    public ResponseEntity<Map<String, Object>> getAuthCustomerTourRequestsPageable(HttpServletRequest httpServletRequest, String q , int page, int size, String sort, String type) {

        String userEmail=(String)httpServletRequest.getAttribute("email");
        User user = userRepository.findByEmailEquals(userEmail);
        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort,type);
        if(q!=null){
            q=q.trim().toLowerCase().replaceAll("-"," ");
        }

        Page<TourRequest> tourRequest = userRepository.getAuthCustomerTourRequestsPageable(/*q,*/user,pageable);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("Message",SuccessMessages.CRITERIA_ADVERT_FOUND);
        responseBody.put("tourRequest",tourRequest);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);



    }
    /*

     */
}
