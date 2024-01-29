package com.realestate.payload.mappers;


import com.realestate.entity.Category;
import com.realestate.entity.CategoryPropertyKey;
import com.realestate.payload.request.CategoryPropertyKeyRequest;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

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

    public List<CategoryPropertyKey> mapCategoryPropertyKeyRequestToListCategoryPropertyKey(List<CategoryPropertyKeyRequest> categoryPropertyKeyRequests, Category category) {

        List<CategoryPropertyKey> categoryPropertyKeys = new ArrayList<>();

        for (CategoryPropertyKeyRequest request : categoryPropertyKeyRequests) {
            CategoryPropertyKey categoryPropertyKey = CategoryPropertyKey.builder()
                    .name(request.getName())
                    .build();
            categoryPropertyKey.setCategory(category);


            categoryPropertyKeys.add(categoryPropertyKey);
        }

        return categoryPropertyKeys;
    }


}
    
    
    
    
    

