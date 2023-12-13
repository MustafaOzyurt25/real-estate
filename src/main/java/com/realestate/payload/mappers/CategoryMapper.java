package com.realestate.payload.mappers;

import com.realestate.entity.Category;
import com.realestate.entity.CategoryPropertyKey;
import com.realestate.payload.request.CategoryRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {


    //DTO --> POJO

    public Category mapCategoryRequestToCategory(CategoryRequest categoryRequest, List<CategoryPropertyKey> categoryPropertyKeySet){

        return Category.builder()
                .title(categoryRequest.getTitle())
                .slug(categoryRequest.getSlug())
                .icon(categoryRequest.getIcon())
                .seq(categoryRequest.getSeq())
                .is_active(categoryRequest.getIs_active())
                .categoryPropertyKeys(categoryPropertyKeySet)
                .create_at(categoryRequest.getCreate_at())
                .build();
    }
}
