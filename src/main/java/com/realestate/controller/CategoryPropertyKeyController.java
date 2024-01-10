package com.realestate.controller;

import com.realestate.entity.CategoryPropertyKey;
import com.realestate.payload.request.CategoryPropertyKeyRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.CategoryPropertyKeyService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryPropertyKeyController {

    

    private final CategoryPropertyKeyService categoryPropertyKeyService;



    //C08
    @PostMapping("/{categoryId}/properties")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryPropertyKey> createCategoryPropertyKey(
            @PathVariable Long categoryId,
            @RequestBody @Valid CategoryPropertyKeyRequest request) {

        return categoryPropertyKeyService.createProperty(categoryId, request);

    }


    //C07
    @GetMapping("/{categoryId}/properties")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseMessage<List<CategoryPropertyKey>> getCategoryPropertyKeysByCategoryId(@PathVariable Long categoryId) {
        return categoryPropertyKeyService.getCategoryPropertyKeysByCategoryId(categoryId);
    }

    //C09
    @PutMapping("/properties/{propertyKeyId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryPropertyKey> updateCategoryPropertyKeyById(@PathVariable Long propertyKeyId, @RequestBody @Valid CategoryPropertyKeyRequest request) {
        return categoryPropertyKeyService.updateCategoryPropertyKeyById(propertyKeyId, request);
    }

    //C10
    @DeleteMapping("/properties/{propertyKeyId}")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseMessage<CategoryPropertyKey> deleteCategoryPropertyKeyByKeyId(@PathVariable("propertyKeyId") Long id) {
        return categoryPropertyKeyService.deleteCategoryPropertyKeyByKeyId(id);
    }
    

}















