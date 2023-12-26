package com.realestate.payload.response;

import com.realestate.entity.*;
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
    private Boolean isActive;
    private Integer viewCount;
    private String location;
    private List<Image> images;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Country country;
    private City city;
    private District district;
    private Category category;
    private AdvertType advertType;
    private List<CategoryPropertyValue> categoryPropertyValues;
    private List<Log> logs;

}