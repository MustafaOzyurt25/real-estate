package com.realestate.service;


import com.realestate.entity.Advert;
import com.realestate.entity.AdvertType;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.AdvertTypeMapper;
import com.realestate.payload.request.AdvertTypeRequest;
import com.realestate.payload.response.AdvertTypeResponse;
import com.realestate.payload.response.ImageResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.AdvertTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertTypeService {

    private final AdvertTypeRepository advertTypeRepository;
    private final AdvertTypeMapper advertTypeMapper;

    public ResponseMessage<AdvertTypeResponse> advertTypeCreate(AdvertTypeRequest advertTypeRequest) {

        AdvertType advertType =advertTypeMapper.mapAdvertTypeRequestToAdvertType(advertTypeRequest);
        AdvertType savedAdvertType=advertTypeRepository.save(advertType);

     return ResponseMessage.<AdvertTypeResponse>builder()
             .object(advertTypeMapper.mapAdvertTypeToAdvertTypeResponse(savedAdvertType))
             .httpStatus(HttpStatus.CREATED)
             .message(SuccessMessages.CREATE_ADVERT_TYPE)
             .build();

    }

    private AdvertType isAdvertTypeExists(Long id){

        return advertTypeRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_TYPE_NOT_FOUND_MESSAGE,id)));

    }
    public AdvertType getAdvertTypeById(Long advertTypeId) {
        return isAdvertTypeExists(advertTypeId);
    }

    public ResponseMessage<AdvertTypeResponse> updateAdvertType(Long advertTypeId) {
        AdvertType existingAdvertType = advertTypeRepository.findById(advertTypeId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_TYPE_NOT_FOUND_MESSAGE, advertTypeId)));
        existingAdvertType.setTitle("New Title");
        return ResponseMessage.<AdvertTypeResponse>builder()
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.UPDATE_ADVERT_TYPE)
                .build();
    }
}
