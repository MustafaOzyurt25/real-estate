package com.realestate.payload.mappers;


import com.realestate.entity.CategoryPropertyKey;
import com.realestate.payload.request.CategoryPropertyKeyRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CategoryPropertyKeyMapper {

    // DTO - > POJO

    public CategoryPropertyKey mapCategoryPropertyKeyRequestToCategoryPropertyKey(CategoryPropertyKeyRequest categoryPropertyKeyRequest) {

        return CategoryPropertyKey.builder()
                .name(categoryPropertyKeyRequest.getName())
                .build();
    }

    // Update icin DTO - > POJO 
    public CategoryPropertyKey mapCategoryPropertyKeyRequestoUpdatedCategoryPropertyKey(Long id, CategoryPropertyKeyRequest request) {
        return mapCategoryPropertyKeyRequestToCategoryPropertyKey(request)
                .toBuilder()
                .id(id)
                .build();

    }

}
    
    
    
    
    

