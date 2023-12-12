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
public class ImageRequest {

    @NotNull
    private byte[] data;

    @NotNull(message = "Please enter a name")
    @Size(min=2,max=50,message = "Name must be between {min} and {max} characters")
    private String name;

    @NotNull(message = "Please enter type")
    private String type;

    @NotNull
    private Boolean featured;
}
