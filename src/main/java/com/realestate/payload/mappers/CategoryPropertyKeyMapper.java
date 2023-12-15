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
                //.built_in(categoryPropertyKeyRequest.get...)  bu db de doldurulacak
                //.category(...)  // bunu servicede tamamladim!
                //.categoryPropertyValue(categoryPropertyKeyRequest.get)  bu relational table geregi var.

                .build();
    }

    // Update icin DTO - > POJO( CUNKU UPDATE VERISI DB DE HAZIR VE ID SI DE VAR )
    public CategoryPropertyKey mapCategoryPropertyKeyRequestoUpdatedCategoryPropertyKey(Long id, CategoryPropertyKeyRequest request) {
        //  @Builder(toBuilder = true) kullanip buna id ekledik.

        return mapCategoryPropertyKeyRequestToCategoryPropertyKey(request)
                .toBuilder() // metodun olusturdugu objeye elimizde zaten varolan id yi eklemek istedik
                .id(id)
                .build();

    }

}
    
    
    
    
    

