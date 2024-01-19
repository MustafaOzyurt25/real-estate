package com.realestate.controller;

import com.realestate.entity.Advert;
import com.realestate.entity.enums.AdvertStatus;
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
import java.util.*;

@RestController
@RequestMapping("/adverts")
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;

    //A10
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PostMapping("/save")
    public Advert save(@ModelAttribute AdvertRequest advertRequest, HttpServletRequest httpServletRequest) {

        return advertService.save(advertRequest, httpServletRequest);

    }

    //A07
    @GetMapping("/{slug}")
    public ResponseMessage<Advert> getAdvertWithSlug(@PathVariable String slug) {
        return advertService.getAdvertWithSlug(slug);
    }


    //A02
    @GetMapping("/cities")
    public List<AdvertCityResponse> getAdvertAmountByCity() {
        return advertService.getAdvertAmountByCity();
    }

    //A03
    @GetMapping("/categories")
    public List<AdvertCategoriesResponse> getAdvertAmountByCategories() {
        return advertService.getAdvertAmountByCategories();
    }

    //A04
    @GetMapping(value = {"/popular", "/popular/{amount}"})
    public List<AdvertResponse> getPopularAdvertsByAmount(@PathVariable(required = false) Integer amount) {
        Integer defaultAmount = (amount == null) ? 10 : amount;
        return advertService.getPopularAdvertsByAmount(defaultAmount);
    }


    //A01
     @GetMapping()
    public ResponseEntity<Map<String, Object>> getSortedAdvertsByValues(@RequestParam(value = "q", required = false) String q,
                                                                        @RequestParam(value = "category_id", required = false) Set<Long> categoryId,
                                                                        @RequestParam(value = "advert_type_id", required = false) Set<Long> advertTypeId,
                                                                        @RequestParam(value = "price_start", required = false) Double priceStart,
                                                                        @RequestParam(value = "price_end", required = false) Double priceEnd,
                                                                        @RequestParam(value = "status", required = false) Integer status,
                                                                        @RequestParam(value = "country_id",required = false) Long countryId,
                                                                        @RequestParam(value = "city_id",required = false) Long cityId,
                                                                        @RequestParam(value = "district_id",required = false) Long districtId,
                                                                        @RequestParam(value = "page",defaultValue = "0") int page,
                                                                        @RequestParam(value = "size",defaultValue = "20") int size,
                                                                        @RequestParam(value = "sort",defaultValue = "category") String sort,
                                                                        @RequestParam(value = "type",defaultValue = "asc") String type){
        return advertService.getSortedAdvertsByValues(q,categoryId,advertTypeId,priceStart,priceEnd,status,countryId,cityId,districtId,page,size,sort,type);
    }

    //A08
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @GetMapping("/{id}/auth")
    public ResponseMessage<AdvertResponse> getAuthenticatedCustomerAdvertById(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return advertService.getAuthenticatedCustomerAdvertById(id, httpServletRequest);
    }

    //A05
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @GetMapping("/auth")
    public Page<AdvertResponse> getAuthenticatedUserAdverts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "20") int size,
                                                            @RequestParam(value = "sort", defaultValue = "categoryId") String sort,
                                                            @RequestParam(value = "type", defaultValue = "asc") String type,
                                                            HttpServletRequest httpServletRequest) {
        return advertService.getAuthenticatedUserAdverts(page, size, sort, type, httpServletRequest);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/{id}/admin")
    public ResponseMessage<AdvertResponse> getAdvertBySlugAdminManager(@PathVariable Long id) {
        return advertService.getAdvertBySlugAdminManager(id);
    }


    //A06
 @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<Map<String, Object>> getSortedAdvertValuesByAdmin(@RequestParam(value = "q", required = false) String q,
                                                                        @RequestParam(value = "category_id", required = false) Set<Long> categoryId,
                                                                        @RequestParam(value = "advert_type_id", required = false) Set<Long> advertTypeId,
                                                                        @RequestParam(value = "price_start", required = false) Double priceStart,
                                                                        @RequestParam(value = "price_end", required = false) Double priceEnd,
                                                                        @RequestParam(value = "status", required = false) Integer status,
                                                                        @RequestParam(value = "country_id",required = false) Long countryId,
                                                                        @RequestParam(value = "city_id",required = false) Long cityId,
                                                                        @RequestParam(value = "district_id",required = false) Long districtId,
                                                                        @RequestParam(value = "page",defaultValue = "0") int page,
                                                                        @RequestParam(value = "size",defaultValue = "20") int size,
                                                                        @RequestParam(value = "sort",defaultValue = "category") String sort,
                                                                        @RequestParam(value = "type",defaultValue = "asc") String type){
        return advertService.getSortedAdvertsByValues(q,categoryId,advertTypeId,priceStart,priceEnd,status,countryId,cityId,districtId,page,size,sort,type);
    }


    //A11
    //---------------updateAuthenticatedCustomersAdvertById ---------------------------//

    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PutMapping("/auth/{advertId}")
    public ResponseMessage<AdvertResponse> updateAuthenticatedCustomersAdvertById(@PathVariable Long advertId, @RequestBody @Valid AdvertUpdateRequest updateRequest,
                                                                                  HttpServletRequest httpServletRequest) {
        return advertService.updateAuthenticatedCustomersAdvertById(advertId, updateRequest, httpServletRequest);
    }


    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PutMapping("/admin/{advertId}")
    public ResponseMessage<AdvertResponse> updateAdminAdvertById(@PathVariable Long advertId, @RequestBody @Valid AdvertUpdateRequest updateRequest,
                                                                 HttpServletRequest httpServletRequest) {

        return advertService.updateAdminAdvertById(advertId, updateRequest, httpServletRequest);
    }


    //A13 advert delete
    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage deleteAdvertById(@PathVariable Long id,HttpServletRequest httpServletRequest) {
        return advertService.deleteAdvertById(id,httpServletRequest);
    }


    // AdvertStatus enum larini frontend tarafinda,(backend doc. da yok) kullanici secebilsin diye yazildi(admin-adverts-page icin)
    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/advert-status")
    public ResponseEntity<List<Map<String, Object>>> getAdvertStatusList() {
        List<AdvertStatus> advertStatusList = Arrays.asList(AdvertStatus.values());

        List<Map<String, Object>> responseList = new ArrayList<>();
        for (AdvertStatus advertStatus : advertStatusList) {
            Map<String, Object> statusMap = new HashMap<>();
            statusMap.put("id", advertStatus.getId());
            statusMap.put("name", advertStatus.name());
            responseList.add(statusMap);
        }
        return ResponseEntity.ok(responseList);
    }

    
}