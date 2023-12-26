package com.realestate.payload.mappers;


import com.realestate.payload.response.StatisticsResponse;
import org.springframework.stereotype.Component;

@Component
public class StatisticsMapper {

    public StatisticsResponse createStatisticsResponse(long publishedCategoriesCount, long publishedAdvertsCount,
                                                       long advertTypesCount, long tourRequestsCount, long customersCount) {

        return StatisticsResponse.builder()
                .advertTypesCount(advertTypesCount)
                .tourRequestsCount(tourRequestsCount)
                .customersCount(customersCount)
                .publishedAdvertsCount(publishedAdvertsCount)
                .publishedCategoriesCount(publishedCategoriesCount)
                .build();

    
    }
}
