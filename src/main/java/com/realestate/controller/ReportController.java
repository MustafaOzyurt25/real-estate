package com.realestate.controller;



import com.realestate.entity.AdvertType;
import com.realestate.entity.Category;
import com.realestate.entity.Advert;
import com.realestate.entity.enums.TourRequestStatus;


import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.StatisticsResponse;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {


    private final ReportService reportService;



        // it will get tour requests for ADMIN,MANAGER ----G05 //




    //  It will get some statistics....   G01.................\\
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping()
    public ResponseMessage<StatisticsResponse> getStatistics() {
        return reportService.getStatistics();
    }


    // it will get tour requests for ADMIN,MANAGER ----G05 //

    @PreAuthorize("hasAnyAuthority('ADMIN,MANAGER')")
    @GetMapping("/tour-requests")
    public ResponseMessage<List<TourRequestResponse>> getTourRequestsReport(
            @RequestParam(value = "date1") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date1,
            @RequestParam(value = "date2") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date2,
            @RequestParam(value = "status", defaultValue = "PENDING") TourRequestStatus status
    ) {
        return reportService.getTourRequestsReport(date1, date2, status);
    }



    @GetMapping("/most-popular-properties")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<List<Advert>> getMostPopularProperties(@RequestParam("amount") int amount) {
        List<Advert> mostPopularProperties = reportService.getMostPopularProperties(amount);
        return new ResponseEntity<>(mostPopularProperties, HttpStatus.OK);
    }



    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/adverts")
    public ResponseMessage<List<AdvertResponse>> getReportAdverts (
            @RequestParam("date1") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date1,
            @RequestParam("date2") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date2,
            @RequestParam("categoryId")Long categoryId,
            @RequestParam("advertTypeId")Long advertTypeId,
            @RequestParam("statusId") int statusId
            ){
        return reportService.getReportAdverts(date1,date2,categoryId,advertTypeId,statusId);
    }






    }




    
    
    
    
    
    
    
    


