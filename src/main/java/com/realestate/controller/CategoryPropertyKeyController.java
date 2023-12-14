package com.realestate.controller;


import com.realestate.entity.CategoryPropertyKey;
import com.realestate.payload.request.CategoryPropertyKeyRequest;

import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.CategoryPropertyKeyService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryPropertyKeyController {


    private final CategoryPropertyKeyService categoryPropertyKeyService;

    // Not: Save() ****************************************************************** C08

    @PostMapping("/{categoryId}/properties") // http://localhost:8080/categories/33/properties  + POST
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<CategoryPropertyKey> createCategoryPropertyKey(
            @PathVariable Long categoryId,
            @RequestBody @Valid CategoryPropertyKeyRequest request) {

        CategoryPropertyKey createdProperty = categoryPropertyKeyService.createProperty(categoryId, request);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }

    // Not: getCategoryPropertyKeysByCategoryId() ****************************************************************** C07

    @GetMapping("/{categoryId}/properties") // http://localhost:8080/categories/4/properties  + GET
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<List<CategoryPropertyKey>> getCategoryPropertyKeysByCategoryId(@PathVariable Long categoryId) {
        return categoryPropertyKeyService.getCategoryPropertyKeysByCategoryId(categoryId);
    }

    // Not: updateByCategoryPropertyKeyId() ********************************************************************* C09


    //http://localhost:8080/categories/properties/45  + PUT + BODY + path
    @PutMapping("/properties/{propertyKeyId}")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<CategoryPropertyKey> updateCategoryPropertyKeyById(@PathVariable Long propertyKeyId, @RequestBody @Valid CategoryPropertyKeyRequest request) {
        return categoryPropertyKeyService.updateCategoryPropertyKeyById(propertyKeyId, request);
    }


}// controller















