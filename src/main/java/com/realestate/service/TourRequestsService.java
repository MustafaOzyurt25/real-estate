package com.realestate.service;


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
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class TourRequestsService {

    private final TourRequestsRepository tourRequestsRepository;
    private final TourRequestMapper tourRequestMapper;
    private final AdvertService advertService;
    private final UserRepository userRepository;
    private final PageableHelper pageableHelper;


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

        return null; // hata almamasi icin eklendi yorumda olan kodlar duzelince silebilirsiniz

    }


    //S05
    public ResponseMessage<TourRequestResponse> save(TourRequestRequest tourRequestRequest, String userEmail) {


        // bu kullanıcı tourrequest oluşturan kullanıcıdır. guest_user_id field'ına kaydedilmesi gerekir.
        // Buradaki email'e sahip user'ı bulup guest_user atamamız gerekiyor.
        System.out.println("User Email : " + userEmail);
        if (!userRepository.existsByEmail(userEmail)) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_BY_EMAIL, userEmail));
        }
        User guestUser = userRepository.findByEmailEquals(userEmail);
        //DTO-->POJO

        TourRequest tourRequest = tourRequestMapper.mapTourRequestRequestToTourRequest(tourRequestRequest);
        tourRequest.setGuestUser(guestUser);


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

    /*S06 --------------------------- kontrol edilecek ---------------------------------------------------------------*/
    public ResponseMessage<TourRequestResponse> updatedTourRequestAuthById(TourRequest tourRequest,

                                                                           Long tourRequestId) {
        TourRequest tourRequestExist = isTourRequestExist(tourRequestId);

        //2-Only the tour requests whose status pending or rejected/DECLINED can be updated./  -Yalnızca beklemede/pending veya reddedilmiş/rejected/DECLINED durumu olan tur talepleri güncellenebilir.
        //--------------------------------------------------------------//pending veya rejected olup olmadigini kontrol et!!!
        if (tourRequestExist.getStatus() == TourRequestStatus.PENDING || tourRequestExist.getStatus() == TourRequestStatus.DECLINED) {
            tourRequestExist.setTourDate(tourRequestExist.getTourDate());
            tourRequestExist.setTourTime(tourRequestExist.getTourTime());
            tourRequestExist.setId(tourRequestExist.getId());//id yi guncelle!!! advert_id mi tour_id mi?-----------guncellenecek------------------


            //3-If a request is updated, the status field should reset to “pending” / -Bir istek güncellenirse durum alanı "beklemede/pending" olarak sifirlamali
            tourRequestExist.setStatus(TourRequestStatus.PENDING);//gonderilen tourRequest guncellenirse pending olarak sifirla!!!

            //1--It will return the updated tour request object/    - Güncellenmis tur istegini nesnesini dondurecektir.
            TourRequest updatedTourRequestAuthById = tourRequestsRepository.save(tourRequest);
            return ResponseMessage.<TourRequestResponse>builder()
                    .message(SuccessMessages.TOUR_REQUEST_UPDATED)
                    .httpStatus(HttpStatus.OK)
                    .object(tourRequestMapper.mapTourRequestToTourRequestResponse(updatedTourRequestAuthById))
                    .build();
        } else {
            return ResponseMessage.<TourRequestResponse>builder()
                    .message(ErrorMessages.TOUR_REQUEST_CANNOT_BE_UPDATED)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

    }

    //ilgili id li tourRequest var mi?
    private TourRequest isTourRequestExist(Long tourRequestId) {
        return tourRequestsRepository.findById(tourRequestId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND, tourRequestId)));
    }
    /*S06 put end ----------------------------------------------------------------------------------------------------*/


    public ResponseEntity<Map<String, Object>> getAuthCustomerTourRequestsPageable(HttpServletRequest httpServletRequest, String q, int page, int size, String sort, String type) {

        String userEmail = (String) httpServletRequest.getAttribute("email");
        User user = userRepository.findByEmailEquals(userEmail);
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        if (q != null) {
            q = q.trim().toLowerCase().replaceAll("-", " ");
        }

        Page<TourRequest> tourRequest = userRepository.getAuthCustomerTourRequestsPageable(/*q,*/user, pageable);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("Message", SuccessMessages.CRITERIA_ADVERT_FOUND);
        responseBody.put("tourRequest", tourRequest);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);


    }

    public ResponseMessage<TourRequestResponse> getAuthTourRequestById(Long tourRequestId) {

        TourRequest getAuthTourRequest = tourRequestsRepository.findById(tourRequestId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND, tourRequestId)));
        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.mapTourRequestToTourRequestResponse(getAuthTourRequest))
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.RETURNED_TOUR_REQUEST_DETAILS)
                .build();
    }


    public ResponseMessage<TourRequestResponse> approveTourRequest(Long tourRequestId) {
        TourRequest tourRequest = tourRequestsRepository.findById(tourRequestId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND, tourRequestId)));
        tourRequest.setStatus(TourRequestStatus.APPROVED);
        tourRequest.setUpdateAt(LocalDateTime.now());
        tourRequestsRepository.save(tourRequest);

        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.mapTourRequestToTourRequestResponse(tourRequest))
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.TOUR_REQUEST_APPROVE)
                .build();
    }

    public ResponseMessage<TourRequestResponse> declineTourRequest(Long id) {
        TourRequest tourRequest = tourRequestsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND)));
        tourRequest.setStatus(TourRequestStatus.DECLINED);
        tourRequest.setUpdateAt(LocalDateTime.now());
        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.mapTourRequestToTourRequestResponse(tourRequestsRepository.save(tourRequest)))
                .message(SuccessMessages.TOUR_REQUEST_SUCCESSFULLY_DECLINED)
                .httpStatus(HttpStatus.OK)
                .build();

    }

}
