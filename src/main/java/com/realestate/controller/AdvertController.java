package com.realestate.controller;

import com.realestate.entity.Advert;
import com.realestate.payload.request.AdvertRequest;
import com.realestate.payload.request.AdvertUpdateRequest;
import com.realestate.payload.response.AdvertCategoriesResponse;
import com.realestate.payload.response.AdvertCityResponse;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adverts")
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;


    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PostMapping("/save")
    public Advert save(@ModelAttribute AdvertRequest advertRequest, HttpServletRequest httpServletRequest){

        return advertService.save(advertRequest , httpServletRequest);

    }

    @GetMapping("/{slug}")
    public ResponseMessage<Advert> getAdvertWithSlug(@PathVariable String slug){
        return advertService.getAdvertWithSlug(slug);
    }


    @GetMapping("/cities")
    public List<AdvertCityResponse> getAdvertAmountByCity(){
        return advertService.getAdvertAmountByCity();
    }

    @GetMapping("/categories")
    public List<AdvertCategoriesResponse> getAdvertAmountByCategories(){return advertService.getAdvertAmountByCategories();}


    //A04
     @GetMapping(value = {"/popular","/popular/{amount}"})
     public List<AdvertResponse> getPopularAdvertsByAmount(@PathVariable(required = false) Integer amount){
         Integer defaultAmount = (amount == null) ? 10 : amount;
             return advertService.getPopularAdvertsByAmount(defaultAmount);
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



 
    //A08
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @GetMapping("/{id}/auth")
    public ResponseMessage<AdvertResponse> getAuthenticatedCustomerAdvertById(@PathVariable Long id, HttpServletRequest httpServletRequest){
        return advertService.getAuthenticatedCustomerAdvertById(id,httpServletRequest);
    }




    //A05

 @PreAuthorize("hasAnyAuthority('CUSTOMER')")
 @GetMapping("/auth")
 public Page<AdvertResponse> getAuthenticatedUserAdverts(@RequestParam(value = "page",defaultValue = "0") int page,
                                                         @RequestParam(value = "size",defaultValue = "20" ) int size,
                                                         @RequestParam(value = "sort",defaultValue = "categoryId") String sort,
                                                         @RequestParam(value = "type",defaultValue = "asc") String type,
                                                         HttpServletRequest httpServletRequest   )
 {
     return advertService.getAuthenticatedUserAdverts(page,size,sort,type,httpServletRequest);
     
 }



 
 // A09


    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/{id}/admin")
    public ResponseMessage<AdvertResponse> getAdvertBySlugAdminManager(@PathVariable Long id){
        return advertService.getAdvertBySlugAdminManager(id);
    }

    @GetMapping("/admin")
    public ResponseEntity<Map<String, Object>> getSortedAdvertValuesByAdmin(@RequestParam(value = "q", required = false) String q,
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


    //---------------updateAuthenticatedCustomersAdvertById ---------------------------//
   
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PutMapping("/auth/{advertId}")
    public ResponseMessage<AdvertResponse> updateAuthenticatedCustomersAdvertById(@PathVariable Long advertId, @RequestBody @Valid AdvertUpdateRequest updateRequest,
                                                                                  HttpServletRequest httpServletRequest){
        return advertService.updateAuthenticatedCustomersAdvertById(advertId,updateRequest,httpServletRequest);
    }

   
    

   
    /*
    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage deleteAdvertById(@PathVariable Long id) {
        return advertService.deleteAdvertById(id);
    }

     */

    
    


}