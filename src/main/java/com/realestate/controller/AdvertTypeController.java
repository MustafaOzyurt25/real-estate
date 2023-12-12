package com.realestate.controller;

import com.realestate.payload.request.AdvertTypeRequest;
import com.realestate.payload.response.AdvertTypeResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.AdvertTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/advert-types")
@RequiredArgsConstructor
public class AdvertTypeController {
    private final AdvertTypeService advertTypeService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<AdvertTypeResponse> advertTypeCreated (@Valid @RequestBody AdvertTypeRequest advertTypeRequest) {
        return advertTypeService.advertTypeCreate (advertTypeRequest);


    }

}
