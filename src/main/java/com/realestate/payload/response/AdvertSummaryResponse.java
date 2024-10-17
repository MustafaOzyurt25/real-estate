package com.realestate.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

// like/tourRequest e  özel bir nesne sınıfı olusturdum.
public class AdvertSummaryResponse {

    private Long advertId;
    private Long favoritesCount;
    private Integer tourRequestsCount;
   

}
