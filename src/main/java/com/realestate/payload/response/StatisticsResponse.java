package com.realestate.payload.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

// İstatistiklere özel bir nesne sınıfı 
public class StatisticsResponse {

    private Long publishedCategoriesCount;
    private Long publishedAdvertsCount;
    private Long advertTypesCount;
    private Long tourRequestsCount;
    private Long customersCount;

}
