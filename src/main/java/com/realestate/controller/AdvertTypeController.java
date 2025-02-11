package com.realestate.controller;

import com.realestate.payload.request.AdvertTypeRequest;
import com.realestate.payload.response.AdvertTypeResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.AdvertTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/advert-types")
@RequiredArgsConstructor
public class AdvertTypeController {
    private final AdvertTypeService advertTypeService;


    //T03
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<AdvertTypeResponse> advertTypeCreated (@Valid @RequestBody AdvertTypeRequest advertTypeRequest) {
        return advertTypeService.advertTypeCreate (advertTypeRequest);

    }




    //T05
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage<AdvertTypeResponse> advertTypeDelete(@PathVariable Long id) {

        return advertTypeService.advertTypeDeleteById(id);

    }



    //T02
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseMessage<AdvertTypeResponse> getAdvertTypeById(@PathVariable("id") Long id){
        return advertTypeService.getAdvertTypeWithId(id);

    }


    //T04
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseMessage<AdvertTypeResponse> updateAdvertType(@PathVariable("id") Long advertTypeId,
                                                                @RequestBody AdvertTypeRequest request) {
        return advertTypeService.updateAdvertType(advertTypeId, request);
    }

    //T01 Get All
    @GetMapping()
    public ResponseMessage<List<AdvertTypeResponse>> getAll() {
        return advertTypeService.getAll();
    }
}
