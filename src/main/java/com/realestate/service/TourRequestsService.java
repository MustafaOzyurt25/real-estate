package com.realestate.service;


import com.realestate.entity.Advert;
import com.realestate.entity.TourRequest;
import com.realestate.entity.User;
import com.realestate.entity.enums.TourRequestStatus;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.helper.PageableHelper;
import com.realestate.payload.mappers.TourRequestMapper;
import com.realestate.payload.request.TourRequestRequest;
import com.realestate.payload.request.UpdateTourRequestRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.payload.response.UpdateTourRequestResponse;
import com.realestate.repository.AdvertRepository;
import com.realestate.repository.AdvertTypeRepository;
import com.realestate.repository.TourRequestsRepository;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TourRequestsService {

    private final TourRequestsRepository tourRequestsRepository;
    private final TourRequestMapper tourRequestMapper;
    private final AdvertService advertService;
    private final UserRepository userRepository;
    private final PageableHelper pageableHelper;

    private final AdvertRepository advertRepository;
    private final AdvertTypeRepository advertTypeRepository;

    //S05
    public ResponseMessage<TourRequestResponse> save(TourRequestRequest tourRequestRequest, String userEmail) {


        // bu kullanıcı tourrequest oluşturan kullanıcıdır. guest_user_id field'ına kaydedilmesi gerekir.
        // Buradaki email'e sahip user'ı bulup guest_user atamamız gerekiyor.

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

        // tour request pending veya approved durumunda zaten varsa kontrolü
        Long advertId= tourRequestRequest.getAdvertId();
        if (tourRequestsRepository.existsByAdvertIdAndGuestUserIdAndStatus(advertId,guestUser.getId())){
            throw new ResourceNotFoundException(ErrorMessages.TOUR_REQUEST_ALREADY_EXIST);
        }

        // kendi ilanına tour request olusturamama durumu
        Advert advert= advertService.getAdvertById(advertId);
        Long userId= advert.getUser().getId(); // advert ı olusturan user i cektik
        if(userId==guestUser.getId()){
            throw new ResourceNotFoundException(ErrorMessages.TOUR_REQUEST_CANNOT_CREATE_OWN_ADVERT);
        }

        //geçmiş tarihe tour request olusturamama kontrolu
        if (tourRequestRequest.getTourDate().isBefore(LocalDate.now())){
            throw new ResourceNotFoundException(ErrorMessages.TOUR_REQUEST_DATE_CANNOT_BE_PAST_DATE);
        }

        //geçmiş saate tour request olusturamama kontrolu
        if (tourRequestRequest.getTourDate().isEqual(LocalDate.now())){
            if (tourRequestRequest.getTourTime().isBefore(LocalTime.now())){
                throw new ResourceNotFoundException(ErrorMessages.TOUR_REQUEST_TIME_CANNOT_BE_PAST_TIME);
            }
        }



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


    /**S06 --- updatedTourRequest ------------------------------------------------------------------------------------*/

    public ResponseMessage<UpdateTourRequestResponse> updatedTourRequest(UpdateTourRequestRequest updateTourRequestRequest, Long tourId) {

        TourRequest tourRequestExist = isTourRequestExist(tourId);//var ise

        if (!isValidTourTime(updateTourRequestRequest.getTourTime())) {// Tur zamani gecerli degil/ yarim ve tam zamanli tur time belirleyebilir
            return ResponseMessage.<UpdateTourRequestResponse>builder()
                    .message(ErrorMessages.INVALID_TOUR_TIME)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        boolean isExistTourRequest = tourRequestsRepository//advertId, time, date i ayni baska tourrequest var mi?
                .existsByAdvertIdAndTourDateAndTourTime(updateTourRequestRequest.getAdvertId(),
                updateTourRequestRequest.getTourDate(),
                updateTourRequestRequest.getTourTime());

        if (isExistTourRequest) {//cakisan tour talepleri
            return ResponseMessage.<UpdateTourRequestResponse>builder()
                    .message(ErrorMessages.TOUR_REQUEST_ALREADY_EXIST)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        /*Only the tour requests whose status PENDING or DECLINED can be updated.*/
        if (tourRequestExist.getStatus() == TourRequestStatus.PENDING || tourRequestExist.getStatus() == TourRequestStatus.DECLINED) {
            tourRequestExist.setTourDate(updateTourRequestRequest.getTourDate());
            tourRequestExist.setTourTime(updateTourRequestRequest.getTourTime());
            tourRequestExist.setUpdateAt(LocalDateTime.now());

        } else {
            return ResponseMessage.<UpdateTourRequestResponse>builder()
                    .message(ErrorMessages.TOUR_REQUEST_CANNOT_BE_UPDATED)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        /*If a request is updated, the status field should reset to “pending” */
        tourRequestExist.setStatus(TourRequestStatus.PENDING);

        /*It will return the updated tour request object*/
        TourRequest updatedTourRequest = tourRequestsRepository.save(tourRequestExist);
        return ResponseMessage.<UpdateTourRequestResponse>builder()
                .message(SuccessMessages.TOUR_REQUEST_UPDATED)
                .httpStatus(HttpStatus.OK)
                .object(tourRequestMapper.tourRequestUpdateResponse(updatedTourRequest))
                .build();

    }

    private TourRequest isTourRequestExist(Long tourId) {
        return tourRequestsRepository.findById(tourId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND, tourId)));//bu id ye ait tourrequest yok ise
    }
    private boolean isValidTourTime(LocalTime tourTime) {
        int minute = tourTime.getMinute();
        return (minute == 00 || minute == 30);//tam ve yarim
    }

    /** S06 end ------------------------------------------------------------------------------------------------------*/



    //S01
  /*  public ResponseEntity<Map<String, Object>> getAuthCustomerTourRequestsPageable(HttpServletRequest httpServletRequest, String q, int page, int size, String sort, String type) {

        String userEmail = (String) httpServletRequest.getAttribute("email");
        User user = userRepository.findByEmailEquals(userEmail);
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        if (q != null) {
            q = q.trim().toLowerCase().replaceAll("-", " ");
        }

        Page<TourRequest> tourRequest = userRepository.getAuthCustomerTourRequestsPageable(/*q user, pageable);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("Message", SuccessMessages.CRITERIA_ADVERT_FOUND);
        responseBody.put("tourRequest", tourRequest.map(tourRequestMapper::mapTourRequestToTourRequestResponse));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);


    }*/


    //S01
    //======================================================
    public Page<TourRequestResponse> getAuthCustomerTourRequestsPageable(HttpServletRequest httpServletRequest, String q, int page, int size, String sort, String type) {
        try {
            String userEmail = (String) httpServletRequest.getAttribute("email");
            User user = userRepository.findByEmailEquals(userEmail);
            Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

            Long guestUserId = user.getId();
            List<TourRequest> userTourRequests = tourRequestsRepository.findByGuestUserId(guestUserId);

            // Filtreleme işlemi "q" parametresiyle
            List<TourRequest> filteredTourRequests = userTourRequests.stream()
                    .filter(tourRequest -> tourRequestMatchesSearchQuery(tourRequest, q))
                    .collect(Collectors.toList());

            // Sayfalama işlemi
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), filteredTourRequests.size());
            Page<TourRequest> paginatedTourRequests = new PageImpl<>(filteredTourRequests.subList(start, end), pageable, filteredTourRequests.size());

            return paginatedTourRequests.map(tourRequestMapper::mapTourRequestToTourRequestResponse);
        } catch (RuntimeException e) {
            throw new ConflictException(ErrorMessages.USER_UNAUTHORIZED);
        }
    }

    private boolean tourRequestMatchesSearchQuery(TourRequest tourRequest, String q) {
        if (q == null || q.trim().isEmpty()) {
            return true; // Eğer arama sorgusu boşsa, herhangi bir filtre uygulanmamış kabul edilir.
        }

        String lowerCaseQuery = q.toLowerCase();

        // Başlık alanlarında anahtar kelimeye göre eşleşme kontrolü
        return tourRequest.getAdvert().getTitle().toLowerCase().contains(lowerCaseQuery);

    }
    //====================================================================================================




    public ResponseEntity<Map<String, Object>> getTourRequestByAdmin(HttpServletRequest httpServletRequest, String q, int page, int size, String sort, String type) {

        String userEmail = (String) httpServletRequest.getAttribute("email");
        User user = userRepository.findByEmailEquals(userEmail);

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        if (q != null) {
            q = q.trim().toLowerCase().replaceAll("-", " ");
        }

        Page<TourRequest> tourRequestPage = userRepository.getTourRequestByAdmin( user, pageable);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("Message", SuccessMessages.CRITERIA_ADVERT_FOUND);
        responseBody.put("tourRequest", tourRequestPage);
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
        TourRequest tourRequest = tourRequestsRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND)));
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

    public List<TourRequest> getTourRequestByUserId(Long userId){
        return tourRequestsRepository.findAll();
    }

    public ResponseMessage<TourRequestResponse> cancelTourRequest(Long id) {
        TourRequest tourRequest = tourRequestsRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.TOUR_REQUEST_NOT_FOUND)));
        tourRequest.setStatus(TourRequestStatus.CANCELED);
        tourRequest.setUpdateAt(LocalDateTime.now());

        return ResponseMessage.<TourRequestResponse>builder()
                .object(tourRequestMapper.mapTourRequestToTourRequestResponse(tourRequestsRepository.save(tourRequest)))
                .message(SuccessMessages.TOUR_REQUEST_SUCCESSFULLY_CANCELED)
                .httpStatus(HttpStatus.OK)
                .build();
    }



}
