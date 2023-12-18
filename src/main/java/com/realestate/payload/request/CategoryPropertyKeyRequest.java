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
    @Size(min = 2, max = 80, message = "catagory property key's name should be between 2 and 80 chars")
    private String name;


}
