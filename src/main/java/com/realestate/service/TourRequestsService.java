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


    //S05
    public ResponseMessage<TourRequestResponse> save(TourRequestRequest tourRequestRequest, String userEmail) {


        // bu kullanıcı tourrequest oluşturan kullanıcıdır. guest_user_id field'ına kaydedilmesi gerekir.
        // Buradaki email'e sahip user'ı bulup guest_user atamamız gerekiyor.
        System.out.println("User Email : " + userEmail);
        if (!userRepository.existsByEmail(userEmail)) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_BY_EMAIL, userEmail));
        }
        User guestUser = userRepository.findByEmailEquals(userEmail);
        Advert ownerUserAdvert = advertService.getAdvertById(tourRequestRequest.getAdvertId());
        //DTO-->POJO

        TourRequest tourRequest = tourRequestMapper.mapTourRequestRequestToTourRequest(tourRequestRequest);
        tourRequest.setGuestUser(guestUser);
        tourRequest.setOwnerUser(ownerUserAdvert.getUser());


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
     * //S06 put   //It will update a tour request -> tur talebini guncelle ----------------------------------------------
     * <p>
     * //1--It will return the updated tour request object/    - Güncellenmis tur istegini nesnesini dondurecektir.
     * <p>
     * //2-Only the tour requests whose status pending or rejected can be updated./  -Yalnızca beklemede/pending veya reddedilmiş/rejected/DECLINED durumu olan tur talepleri güncellenebilir.
     * --------------------------------------------------------------//pending veya rejected olup olmadigini kontrol et!!!
     * //3-If a request is updated, the status field should reset to “pending” / -Bir istek güncellenirse durum alanı "beklemede/pending" olarak sıfırlanmalıdır
     * ---------------------------------------------------//gonderilen tourRequest guncellenirse pending olarak sifirla!!!
     * <p>
     * <p>
     * public static ResponseMessage<TourRequestResponse> updatedTourRequest(TourRequest tourRequest, Long tourRequestId) {
     * //!!! id kontrol ---> pending veya reddedilmis tur taleplerini guncelle. update edilecek tourrequest var mi?
     * <p>
     * TourRequest tourRequest1 = isTourRequestExist(tourRequestId);
     * <p>
     * //-----------------------------------------------------------------------------------------------------------------
     * <p>
     * public ResponseMessage<TourRequestResponse> updatedTourRequest(Long tourRequestId, TourRequestRequest TourRequestRequest) {
     * TourRequest tourRequest = tourRequestsRepository.findById(tourRequestId)
     * .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND, tourRequestId)));
     * <p>
     * // Sadece "pending" veya "rejected/DECLINED" durumundaki istekleri güncelle -> REJECTED dokumantasyonda yazan, projede yazan DECLINED!!!
     * if (tourRequest.getStatus() == TourRequestStatus.PENDING || tourRequest.getStatus() == TourRequestStatus.DECLINED) {
     * tourRequest.setTourDate(TourRequestRequest.getTourDate());
     * tourRequest.setTourTime(TourRequestRequest.getTourTime());
     * // tourRequest.setTourId(TourRequestRequest.getTourId()); ---> TODO advert_id, tour_id ?????????????????????????
     * <p>
     * //gonderilen tourRequest guncellenirse pending olarak sifirla!!!
     * tourRequest.setStatus(TourRequestStatus.PENDING);
     * <p>
     * <p>
     * TourRequest updatedTourRequest = tourRequestsRepository.save(tourRequest);
     * return ResponseMessage.<TourRequestResponse>builder()
     * .message(SuccessMessages.TOUR_REQUEST_UPDATED)
     * .httpStatus(HttpStatus.OK)
     * .object(tourRequestMapper.mapTourRequestToTourRequestResponse(updatedTourRequest))
     * .build();
     * } else {
     * return ResponseMessage.<TourRequestResponse>builder()
     * .message(ErrorMessages.TOUR_REQUEST_CANNOT_BE_UPDATED)
     * .httpStatus(HttpStatus.BAD_REQUEST)
     * .build();
     * }
     * }
     */


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


    public boolean controlTourRequestByUserId(Long userId) {
        
        boolean isExistsByGuestUserId = tourRequestsRepository.existsByGuestUserId(userId);
        boolean isExistsByOwnerUserId = tourRequestsRepository.existsByOwnerUserId(userId);

        return isExistsByGuestUserId || isExistsByOwnerUserId;

    }
        public ResponseMessage<TourRequestResponse> cancelTourRequest (Long id){
            TourRequest tourRequest = tourRequestsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND)));
            tourRequest.setStatus(TourRequestStatus.CANCELED);
            tourRequest.setUpdateAt(LocalDateTime.now());

            return ResponseMessage.<TourRequestResponse>builder()
                    .object(tourRequestMapper.mapTourRequestToTourRequestResponse(tourRequestsRepository.save(tourRequest)))
                    .message(SuccessMessages.TOUR_REQUEST_SUCCESSFULLY_CANCELED)
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
    }
