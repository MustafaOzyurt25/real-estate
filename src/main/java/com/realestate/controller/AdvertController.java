package com.realestate.controller;

import com.realestate.entity.Advert;
import com.realestate.payload.request.AdvertRequest;
import com.realestate.payload.response.AdvertCityResponse;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/adverts")
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;

    @PostMapping("/save")
    public Advert save(@ModelAttribute AdvertRequest advertRequest){

        return advertService.save(advertRequest);

    }

    @GetMapping("/{slug}")
    public ResponseMessage<Advert> getAdvertWithSlug(@PathVariable String slug){
        return advertService.getAdvertWithSlug(slug);
    }


    @GetMapping("/cities")
    public List<AdvertCityResponse> getAdvertAmountByCity(){
        return advertService.getAdvertAmountByCity();
    }

     @GetMapping(("/popular/{amount}"))
     public List<AdvertResponse> getPopularAdvertsByAmount(@PathVariable Integer amount){
             return advertService.getPopularAdvertsByAmount(amount);
      }


    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @GetMapping("/{id}/auth")
    public ResponseMessage<AdvertResponse> getAuthenticatedCustomerAdvertById(@PathVariable Long id){
        return advertService.getAuthenticatedCustomerAdvertById(id);
    }
}