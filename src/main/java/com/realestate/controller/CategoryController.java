package com.realestate.controller;

import com.realestate.entity.Category;
import com.realestate.payload.request.CategoryRequest;
import com.realestate.payload.response.CategoryResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    //C04---------------------------------------------------------------------------------------------------------------
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public Category createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {


        return categoryService.createCategory(categoryRequest);
    }


    //C06
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    //@PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseMessage<CategoryResponse> deleteCategory(@PathVariable("id") Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }


    //C01
    @GetMapping("/getAllCategoriesByPage")
    public ResponseEntity<Map<String, Object>> getAllCategoriesByPage(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        return categoryService.getAllCategoriesByPage(q, page, size, sort, type);
    }


    //C03
    @GetMapping("/getById/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }


    //C02
    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public Page<CategoryResponse> getAllCategories( @RequestParam(value = "q", required = false) String q,
                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "size", defaultValue = "20") int size,
                                                    @RequestParam(value = "sort", defaultValue = "category_id") String sort,
                                                    @RequestParam(value = "type", defaultValue = "asc") String type) {

        return categoryService.getAllCategoriesByPageWithAdmin(q, page, size, sort, type);
    }

    //C05
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public ResponseMessage<CategoryResponse> updateCategoryWithId(@PathVariable("id") Long id, @RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.updateCategoryWithId(id, categoryRequest);
    }

}


