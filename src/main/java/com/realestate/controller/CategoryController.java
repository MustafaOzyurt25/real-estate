package com.realestate.controller;

import com.realestate.entity.Category;
import com.realestate.payload.request.CategoryRequest;
import com.realestate.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public Category createCategory(@RequestBody @Valid CategoryRequest categoryRequest){

        return categoryService.createCategory(categoryRequest);
    }
}
