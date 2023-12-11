package com.realestate.controller;


import com.realestate.payload.request.TourRequestRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.service.TourRequestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tour-requests")
@RequiredArgsConstructor
public class TourRequestsController {
    private final TourRequestsService tourRequestsService;

    //@PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PostMapping("save")
    public ResponseMessage<TourRequestResponse> save(@RequestBody TourRequestRequest tourRequestRequest){
        return tourRequestsService.save(tourRequestRequest);

    }
}
