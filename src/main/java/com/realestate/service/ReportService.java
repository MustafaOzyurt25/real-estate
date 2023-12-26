package com.realestate.service;


import com.realestate.entity.Advert;
import com.realestate.entity.TourRequest;
import com.realestate.entity.enums.TourRequestStatus;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.TourRequestMapper;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.repository.AdvertRepository;
import com.realestate.repository.TourRequestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TourRequestsRepository tourRequestsRepository;
    private final TourRequestMapper tourRequestMapper;
    private final AdvertRepository advertRepository;

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

    public List<Advert> getMostPopularProperties(int amount) {
        return advertRepository.findTopNByTourRequestsOrderByTourRequestsDesc(amount);

    }
}
