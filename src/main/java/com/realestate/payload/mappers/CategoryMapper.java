package com.realestate.payload.mappers;

import com.realestate.entity.Category;
import com.realestate.entity.CategoryPropertyKey;
import com.realestate.payload.request.CategoryRequest;
import com.realestate.payload.response.CategoryResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CategoryMapper {


    //DTO --> POJO

    public Category mapCategoryRequestToCategory(CategoryRequest categoryRequest){

        return Category.builder()
                .title(categoryRequest.getTitle())
                .icon(categoryRequest.getIcon())
                .seq(categoryRequest.getSeq())
                .slug(categoryRequest.getSlug())
                .isActive(categoryRequest.getIsActive())
                //.categoryPropertyKeys(categoryPropertyKeySet)
                .createAt(categoryRequest.getCreateAt())
                .build();
    }


    //POJO --> DTO
    public CategoryResponse mapCategoryToCategoryResponse(Category category){

        return CategoryResponse.builder()
                .categoryId(category.getId())
                .title(category.getTitle())
                .icon(category.getIcon())
                .seq(category.getSeq())
                .slug(category.getSlug())
                .isActive(category.getIsActive())
                .categoryPropertyKeys(category.getCategoryPropertyKeys())
                .createAt(category.getCreateAt())
                .updateAt(category.getUpdateAt())
                .build();
    }

    public Category mapCategoryRequestToUpdatedCategory(Long id, CategoryRequest categoryRequest,List<CategoryPropertyKey> categoryPropertyKeys) {
        Category category = mapCategoryRequestToCategory(categoryRequest);
        category.setId(id);
        category.setUpdateAt(LocalDateTime.now());
        category.setBuiltIn(false);
        return category;
    }
}
