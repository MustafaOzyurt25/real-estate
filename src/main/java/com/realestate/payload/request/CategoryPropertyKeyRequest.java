package com.realestate.payload.request;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryPropertyKeyRequest {

    @NotNull(message = "Please enter category property key's name")
    @Size(min=2,max=80,message = "catagory property's key name should be at least 2 chars")
    private String name;  // villa category'sinde bir dairenin, cat. prop. keyleri isitma, kacinci kat oldugu  vs olur

    @NotNull(message = "Please enter category")
    private Long categoryId;  // id=5 category (mesela apt dairesi).
    
    
}
