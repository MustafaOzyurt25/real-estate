package com.realestate.controller;


import com.realestate.payload.request.TourRequestRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.repository.UserRepository;
import com.realestate.service.TourRequestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tour-requests")
@RequiredArgsConstructor
public class TourRequestsController {
    private final TourRequestsService tourRequestsService;


    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PostMapping("/save")
    public ResponseMessage<TourRequestResponse> save(@RequestBody TourRequestRequest tourRequestRequest, HttpServletRequest request)
    {
        String userEmail = (String) request.getAttribute("email");
        return tourRequestsService.save(tourRequestRequest , userEmail);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<TourRequestResponse> delete(@PathVariable("id") Long id) {
        return tourRequestsService.delete(id);
    }

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<TourRequestResponse> getTourRequestById(@PathVariable("id") Long tourRequestId) {
        return tourRequestsService.getTourRequestById(tourRequestId);
    }


    @GetMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<TourRequestResponse> getAuthTourRequestById(@PathVariable("id") Long tourRequestId){
        return tourRequestsService.getAuthTourRequestById(tourRequestId);
    }


    @GetMapping("/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<Map<String, Object>> getAuthCustomerTourRequestsPageable(HttpServletRequest httpServletRequest,
                                                                                   @RequestParam(value = "q", required = false) String q,
                                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                   @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                                                   @RequestParam(value = "type", defaultValue = "desc") String type) {
        return tourRequestsService.getAuthCustomerTourRequestsPageable(httpServletRequest , q, page, size, sort, type);


    }


    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<TourRequestResponse> approveTourRequest (@PathVariable("id") Long tourRequestId){
        return tourRequestsService.approveTourRequest(tourRequestId);
    }




    @PatchMapping("/{id}/decline")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<TourRequestResponse> declineTourRequest(@PathVariable("id") Long id){
        return tourRequestsService.declineTourRequest(id);
    }


}
