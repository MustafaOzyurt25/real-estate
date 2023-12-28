package com.realestate.controller;
import com.realestate.entity.enums.TourRequestStatus;
import com.realestate.payload.response.*;
import com.realestate.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {


    private final ReportService reportService;



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

   //G04 It will get users



    /*
    @GetMapping("/most-popular-properties")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<List<Advert>> getMostPopularProperties(@RequestParam Integer amount) {
        List<Advert> mostPopularProperties = reportService.getMostPopularProperties(amount);
        return new ResponseEntity<>(mostPopularProperties, HttpStatus.OK);
    }
    */


    } 
    
    
    
    
    
    
    
    
    
    
    
    

