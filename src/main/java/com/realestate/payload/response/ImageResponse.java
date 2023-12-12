package com.realestate.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ImageResponse {

    private Long imageId;
    private String name;
    private String type;
    private Boolean featured;
    private byte[] data;

}
