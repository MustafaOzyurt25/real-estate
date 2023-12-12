package com.realestate.payload.mappers;

import com.realestate.entity.Category;
import com.realestate.payload.request.CategoryRequest;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    /*
    //DTO --> POJO
    public Category mapCategoryRequestToCategory(CategoryRequest categoryRequest){
        return Category.builder()
                .title(categoryRequest.getTitle())
                .slug(categoryRequest.getSlug())
                .icon(categoryRequest.getIcon())
                .seq(categoryRequest.getSeq())
                .is_active(categoryRequest.getIs_active())
                .categoryPropertiesKeyId(categoryRequest.getCategoryPropertiesKeyId())
                .build();
    }
     */
}
