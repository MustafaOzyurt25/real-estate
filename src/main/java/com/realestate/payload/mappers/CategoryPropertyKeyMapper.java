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
                //.built_in(categoryPropertyKeyRequest.get)  bu db de doldurulacak
                //.category(...)  // bunu servicede tamamladim!
                //.categoryPropertyValue(categoryPropertyKeyRequest.get)  bu relational table geregi var.
                .build();
    }

    
//    // POJO - > DTO   gerek yok buna!
//    public CategoryPropertyKeyResponse mapCategoryPropertyKeyToCategoryPropertyKeyResponse(CategoryPropertyKey categoryPropertyKey){
//        
//        return CategoryPropertyKeyResponse.builder()
//                .id(categoryPropertyKey.getId())
//                .category(categoryPropertyKey.getCategory())
//                .name(categoryPropertyKey.getName())
//                .build();

}
    
    
    
    
    

