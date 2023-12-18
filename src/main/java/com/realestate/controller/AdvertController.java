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
import java.util.Map;

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

    @GetMapping()
    public ResponseEntity<Map<String, Object>> getSortedAdvertsByValues(@RequestParam(value = "q", required = false) String q,
                                                                        @RequestParam(value = "category_id", required = false) Long categoryId,
                                                                        @RequestParam(value = "advert_type_id", required = false) Long advertTypeId,
                                                                        @RequestParam(value = "price_start", required = false) Double priceStart,
                                                                        @RequestParam(value = "price_end", required = false) Double priceEnd,
                                                                        @RequestParam(value = "status", required = false) Integer status,
                                                                        @RequestParam(value = "page",defaultValue = "0") int page,
                                                                        @RequestParam(value = "size",defaultValue = "20") int size,
                                                                        @RequestParam(value = "sort",defaultValue = "category") String sort,
                                                                        @RequestParam(value = "type",defaultValue = "asc") String type){
        return advertService.getSortedAdvertsByValues(q,categoryId,advertTypeId,priceStart,priceEnd,status,page,size,sort,type);
    }
//a

}