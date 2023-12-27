package com.realestate.service;


import com.realestate.entity.Advert;
import com.realestate.entity.AdvertType;
import com.realestate.entity.Category;
import com.realestate.entity.TourRequest;
import com.realestate.entity.enums.AdvertStatus;
import com.realestate.entity.enums.TourRequestStatus;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.AdvertMapper;
import com.realestate.payload.mappers.TourRequestMapper;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.repository.AdvertRepository;
import com.realestate.repository.AdvertTypeRepository;
import com.realestate.repository.CategoryRepository;
import com.realestate.repository.TourRequestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TourRequestsRepository tourRequestsRepository;
    private final TourRequestMapper tourRequestMapper;
    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;
    private final CategoryRepository categoryRepository;
    private final AdvertTypeRepository advertTypeRepository;



    public ResponseMessage<List<TourRequestResponse>> getTourRequestsReport(LocalDate startDate, LocalDate endDate, TourRequestStatus status) {

        List<TourRequest> tourRequests = tourRequestsRepository.findByTourDateBetweenAndStatus(startDate, endDate, status);

        if (tourRequests.isEmpty()) {
            throw new RuntimeException(ErrorMessages.REPORT_TOUR_REQUEST_NOT_FOUND);
        }

        List<TourRequestResponse> tourRequestResponses = tourRequests.stream().map(tourRequestMapper::mapTourRequestToTourRequestResponse).collect(Collectors.toList());

        return ResponseMessage.<List<TourRequestResponse>>builder()
                .object(tourRequestResponses)
                .message(SuccessMessages.REPORT_TOUR_REQUEST)
                .httpStatus(HttpStatus.OK).build();


    }

    public ResponseMessage<List<AdvertResponse>> getReportAdverts(LocalDate date1, LocalDate date2, Long categoryId, Long advertTypeId, int statusId) {


        AdvertStatus advertStatus=AdvertStatus.getAdvertStatusByNumber(statusId);
        LocalDateTime localDateTime1 = date1.atStartOfDay();
        LocalDateTime localDateTime2 = date2.atStartOfDay();


        List<Advert> adverts = advertRepository.findAdvertsByParameters(localDateTime1,localDateTime2,categoryId,advertTypeId,advertStatus);

        if(adverts.isEmpty()){
            throw new RuntimeException(ErrorMessages.ADVERT_NOT_FOUND_EXCEPTION);
        }

        return ResponseMessage.<List<AdvertResponse>>builder()
                .object( adverts.stream().map(advertMapper::mapAdvertToAdvertResponse).collect(Collectors.toList()))
                .message(SuccessMessages.REPORT_ADVERTS)
                .httpStatus(HttpStatus.OK)
                .build();

    }


}
