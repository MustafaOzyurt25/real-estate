package com.realestate.service;

import com.realestate.entity.Advert;
import com.realestate.entity.TourRequest;
import com.realestate.entity.enums.TourRequestStatus;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.StatisticsMapper;
import com.realestate.payload.mappers.TourRequestMapper;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.StatisticsResponse;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final AdvertRepository advertRepository;
    private final CategoryRepository categoryRepository;
    private final TourRequestsRepository tourRequestRepository;
    private final AdvertTypeRepository advertTypeRepository;
    private final UserRepository userRepository;
    private final TourRequestMapper tourRequestMapper;
    private final StatisticsMapper statisticsMapper;


    public ResponseMessage<List<TourRequestResponse>> getTourRequestsReport(LocalDate startDate, LocalDate endDate, TourRequestStatus status) {

        List<TourRequest> tourRequests = tourRequestRepository.findByTourDateBetweenAndStatus(startDate, endDate, status);

        if (tourRequests.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessages.REPORT_TOUR_REQUEST_NOT_FOUND);
        // throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.REPORT_TOUR_REQUEST_NOT_FOUND); alternatif
        }

        List<TourRequestResponse> tourRequestResponses = tourRequests
                .stream()
                .map(tourRequestMapper::mapTourRequestToTourRequestResponse)
                .collect(Collectors.toList());

        return ResponseMessage.<List<TourRequestResponse>>builder()
                .object(tourRequestResponses)
                .message(SuccessMessages.REPORT_TOUR_REQUEST)
                .httpStatus(HttpStatus.OK).build();


    }


    public List<Advert> getMostPopularProperties(Long amount) {
        return advertRepository.findTopNByOrderByTourRequestsDesc(amount);



    //  It will get some statistics....   G01.................\\
    public ResponseMessage<StatisticsResponse> getStatistics() {

        long publishedCategoriesCount = getPublishedCategoriesCount();
        long publishedAdvertsCount = getPublishedAdvertsCount();
        long advertTypesCount = getAdvertTypesCount();
        long tourRequestsCount = getTourRequestsCount();
        long customersCount = getCustomersCount();

        StatisticsResponse statisticsResponse = statisticsMapper.createStatisticsResponse(publishedCategoriesCount,
                publishedAdvertsCount, advertTypesCount,
                tourRequestsCount, customersCount);

        return ResponseMessage.<StatisticsResponse>builder()
                .object(statisticsResponse)
                .message(SuccessMessages.REPORT_STATISTICS_FOUNDED)
                .httpStatus(HttpStatus.OK)
                .build();


    }

    private long getPublishedCategoriesCount() {
        return categoryRepository.countPublishedCategories();

    }

    private long getPublishedAdvertsCount() {
        return advertRepository.countPublishedAdverts();
    }

    private long getAdvertTypesCount() {
        return advertTypeRepository.count();
    }

    private long getTourRequestsCount() {
        return tourRequestRepository.count();
    }

    private long getCustomersCount() {
        return userRepository.countCustomers();
    }



 
}
