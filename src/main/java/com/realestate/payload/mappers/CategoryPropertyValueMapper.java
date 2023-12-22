package com.realestate.payload.mappers;

import com.realestate.entity.Advert;
import com.realestate.entity.CategoryPropertyKey;
import com.realestate.entity.CategoryPropertyValue;
import org.springframework.stereotype.Component;

@Component
public class CategoryPropertyValueMapper {
    public CategoryPropertyValue mapValuesToCategoryPropertyValue(Advert advert, CategoryPropertyKey categoryPropertyKey, String value){
        return CategoryPropertyValue.builder()
                .advert(advert)
                .categoryPropertyKey(categoryPropertyKey)
                .value(value)
                .build();
    }
}
