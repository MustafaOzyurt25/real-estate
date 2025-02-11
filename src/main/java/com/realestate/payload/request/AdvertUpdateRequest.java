package com.realestate.payload.request;


import com.realestate.entity.CategoryPropertyKey;
import com.realestate.entity.enums.AdvertStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvertUpdateRequest {

    @NotNull
    @Size(min=5,max=150,message = "The number of characters in the title must be between 5 and 150")
    private String title;

    @NotNull
    private String slug;
    @Size(max=300,message = "The number of characters in the description max 300")
    private String description;
    @NotNull
    private Double price;
    @NotNull
    private String location; // bu document  de yok.ama gorselde var.
    private int statusId;
    private Long advertTypeId;
    private Long countryId;
    private Long cityId;
    private Long districtId;
    private Long categoryId;
    private Boolean isActive;
    private List<String> propertyValues;
    
    
            
    
    

   
   
    
    
    
    
    
    
}
