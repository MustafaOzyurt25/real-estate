package com.realestate.controller;


import com.realestate.entity.Advert;
import com.realestate.entity.enums.TourRequestStatus;


import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.service.AdvertService;
import com.realestate.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    
    

    private final ReportService reportService;

    
        // it will get tour requests for ADMIN,MANAGER ----G05 //
    
    @PreAuthorize("hasAnyAuthority('ADMIN,MANAGER')")
    @GetMapping("/tour-requests")
    public ResponseMessage<List<TourRequestResponse>> getTourRequestsReport(
            @RequestParam("date1") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date1,// yyyy-MM-dd
            @RequestParam("date2") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date2,
            @RequestParam("status") TourRequestStatus status
    ){
        return reportService.getTourRequestsReport(date1,date2,status);
    }


    @GetMapping("/most-popular-properties")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<List<Advert>> getMostPopularProperties(@RequestParam("amount") int amount) {
        List<Advert> mostPopularProperties = reportService.getMostPopularProperties(amount);
        return new ResponseEntity<>(mostPopularProperties, HttpStatus.OK);
    }

    
    
    
    
    
    
    
    
    
    
    
    
}
