package com.realestate.payload.mappers;


import com.realestate.entity.AdvertType;
import com.realestate.payload.request.AdvertTypeRequest;
import com.realestate.payload.response.AdvertTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class AdvertTypeMapper {
    public AdvertType mapAdvertTypeRequestToAdvertType (AdvertTypeRequest advertTypeRequest) {
        return AdvertType.builder()
                .title(advertTypeRequest.getTitle())
                .build();
    }
    public AdvertTypeResponse mapAdvertTypeToAdvertTypeResponse (AdvertType advertType) {
        return AdvertTypeResponse.builder()
                .id(advertType.getId())
                .title(advertType.getTitle())
                .build();
    }
    public AdvertType mapAdvertTypeRequestToUpdateAdvertType (AdvertTypeRequest advertTypeRequest, Long id) {
        return AdvertType.builder()
                .id(id)
                .title(advertTypeRequest.getTitle())
                .build();
    }
}
