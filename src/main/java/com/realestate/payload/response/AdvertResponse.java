package com.realestate.payload.response;

import com.realestate.entity.City;
import com.realestate.entity.Country;
import com.realestate.entity.District;
import com.realestate.entity.Image;
import com.realestate.entity.enums.AdvertStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvertResponse {

    private Long advertId;
    private String title;
    private String description;
    private String slug;
    private Double price;
    private AdvertStatus advertStatus;
    private Boolean is_active;
    private Integer view_count;
    private String location;
    private List<Image> images;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private Country country;
    private City city;
    private District district;

}