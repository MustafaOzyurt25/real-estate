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
public class AdvertTypeRequest {

    @NotNull(message = "Please enter a Title")
    @Size(min = 10, max = 30, message = "Title should be at least 10 chars")
    private String title;

}
