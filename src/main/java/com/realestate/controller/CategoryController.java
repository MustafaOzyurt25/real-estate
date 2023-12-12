package com.realestate.controller;

import com.realestate.entity.Category;
import com.realestate.payload.request.CategoryRequest;
import com.realestate.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/save")
    public Category save(CategoryRequest categoryRequest){

        return categoryService.save(categoryRequest);
    }
}
