package com.realestate.controller;

import com.realestate.entity.Category;
import com.realestate.payload.request.CategoryRequest;
import com.realestate.payload.response.CategoryResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public Category createCategory(@RequestBody @Valid CategoryRequest categoryRequest){

        return categoryService.createCategory(categoryRequest);
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseMessage<CategoryResponse> deleteCategory(@PathVariable("id") Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/getAllCategoriesByPage")
    public Page<CategoryResponse> getAllCategoriesByPage (
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "type") String type
    ){
        return categoryService.getAllCategoriesByPage(page, size, sort, type);
    }


    @GetMapping("/getById{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }




}
