package com.realestate.payload.response;


import com.realestate.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryPropertyKeyResponse {

    private Long id;

    private String name;
    
    private Category category; // burda pojo gonderdim !
    
                                // value ve built_in i gondermedim.
    
    
    
    
    
}
