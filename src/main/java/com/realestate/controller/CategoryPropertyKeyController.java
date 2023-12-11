package com.realestate.controller;


import com.realestate.entity.CategoryPropertyKey;
import com.realestate.payload.request.CategoryPropertyKeyRequest;

import com.realestate.service.CategoryPropertyKeyService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryPropertyKeyController {


    private final CategoryPropertyKeyService categoryPropertyKeyService;

    // Not: Save() ******************************************************************

    @PostMapping("/{categoryId}/properties") // http://localhost:8080/categories/33/properties  + POST
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<CategoryPropertyKey> createCategoryPropertyKey(
            @PathVariable Long categoryId,
            @RequestBody @Valid CategoryPropertyKeyRequest request) {

        CategoryPropertyKey createdProperty = categoryPropertyKeyService.createProperty(categoryId, request);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }


}















