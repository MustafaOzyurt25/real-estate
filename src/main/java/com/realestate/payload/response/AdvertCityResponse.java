package com.realestate.payload.response;

import com.realestate.entity.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvertCityResponse {

    private String city;
    private Long amount;
    private Long id;

}
