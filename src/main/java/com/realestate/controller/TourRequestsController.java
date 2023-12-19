package com.realestate.controller;


import com.realestate.payload.request.TourRequestRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.service.TourRequestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/tour-requests")
@RequiredArgsConstructor
public class TourRequestsController {
    private final TourRequestsService tourRequestsService;



    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PostMapping("/save")
    public ResponseMessage<TourRequestResponse> save(@RequestBody TourRequestRequest tourRequestRequest){
        return tourRequestsService.save(tourRequestRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<TourRequestResponse> delete(@PathVariable("id") Long id){
        return tourRequestsService.delete(id);
    }
    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<TourRequestResponse> getTourRequestById(@PathVariable("id") Long tourRequestId){
        return tourRequestsService.getTourRequestById(tourRequestId);
    }
}
