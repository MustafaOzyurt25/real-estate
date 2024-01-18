package com.realestate.controller;


import com.realestate.entity.TourRequest;
import com.realestate.payload.request.TourRequestRequest;
import com.realestate.payload.request.UpdateTourRequestRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.payload.response.UpdateTourRequestResponse;
import com.realestate.payload.response.UserResponse;
import com.realestate.service.TourRequestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/tour-requests")
@RequiredArgsConstructor
public class TourRequestsController {
    private final TourRequestsService tourRequestsService;





    //S05
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PostMapping("/save")
    public ResponseMessage<TourRequestResponse> save(@RequestBody @Valid TourRequestRequest tourRequestRequest, HttpServletRequest request)
    {
        String userEmail = (String) request.getAttribute("email");
        return tourRequestsService.save(tourRequestRequest , userEmail);

    }


    //S10
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<TourRequestResponse> delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        return tourRequestsService.delete(id,httpServletRequest);
    }

    //S04
    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<TourRequestResponse> getTourRequestById(@PathVariable("id") Long tourRequestId) {
        return tourRequestsService.getTourRequestById(tourRequestId);
    }

    /**S06 put It will update a tour request -------------------------------------------------------------------------*/

    @PutMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'MANAGER', 'ADMIN')") //http://localhost:8080/tour-requests/{id}/auth + PUT //manager ve admin ekledim
    public ResponseMessage<UpdateTourRequestResponse> updatedTourRequest(@RequestBody @Valid UpdateTourRequestRequest updateTourRequestRequest,
                                                                                 @PathVariable(name = "id") Long tourId){
        return tourRequestsService.updatedTourRequest(updateTourRequestRequest, tourId );
    }

    //S03
    @GetMapping("/{id}/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<TourRequestResponse> getAuthTourRequestById(@PathVariable("id") Long tourRequestId){
        return tourRequestsService.getAuthTourRequestById(tourRequestId);
    }


    //S01
   /* @GetMapping("/auth")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<Map<String, Object>> getAuthCustomerTourRequestsPageable(HttpServletRequest httpServletRequest,
                                                                                   @RequestParam(value = "q", required = false) String q,
                                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                   @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                                                   @RequestParam(value = "type", defaultValue = "desc") String type) {
        return tourRequestsService.getAuthCustomerTourRequestsPageable(httpServletRequest , q, page, size, sort, type);

    }*/
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @GetMapping("/auth")
    public Page<TourRequestResponse> getAllUsersByPage(HttpServletRequest httpServletRequest,
                                                @RequestParam(value = "q", required = false) String q,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "20") int size,
                                                @RequestParam(value = "sort", defaultValue = "categoryId") String sort,
                                                @RequestParam(value = "type", defaultValue = "asc") String type)
    {
        return tourRequestsService.getAuthCustomerTourRequestsPageable(httpServletRequest,q,page,size,sort,type);
    }



    //S01


    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> getTourRequestByAdmin (HttpServletRequest httpServletRequest,
                                                                      @RequestParam(value = "q", required = false) String q,
                                                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                                                      @RequestParam(value = "size", defaultValue = "20") int size,
                                                                      @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                                      @RequestParam(value = "type", defaultValue = "desc") String type) {
        return tourRequestsService.getTourRequestByAdmin(httpServletRequest , q, page, size, sort, type);
    }


    //S08
    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<TourRequestResponse> approveTourRequest (@PathVariable("id") Long tourRequestId){
        return tourRequestsService.approveTourRequest(tourRequestId);
    }




    //S09
    @PatchMapping("/{id}/decline")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage<TourRequestResponse> declineTourRequest(@PathVariable("id") Long id){
        return tourRequestsService.declineTourRequest(id);
    }

    //S07
    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public  ResponseMessage<TourRequestResponse> cancelTourReguest(@PathVariable("id") Long id){
        return tourRequestsService.cancelTourRequest(id);
    }


}
