package com.realestate.controller;

import com.realestate.entity.Category;
import com.realestate.payload.request.CategoryRequest;
import com.realestate.payload.response.CategoryResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    //C04
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
    public ResponseEntity<Map<String, Object>> getAllCategoriesByPage (
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "20") int size,
            @RequestParam(value = "sort",defaultValue = "id") String sort,
            @RequestParam(value = "type",defaultValue = "asc") String type
    )
    {
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
    public ResponseEntity<List<Category>> getAllCategories(@RequestParam(value = "q", required = false) String q,
                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "20") int size,
                                                           @RequestParam(value = "sort", defaultValue = "category_id") String sort,
                                                           @RequestParam(value = "type", defaultValue = "asc") String type) {
        List<Category> categories = categoryService.getAllCategories(q, page, size, sort, type);

        if (q != null && !q.isEmpty()) {
            categories = categories.stream()
                    .filter(category -> category.getTitle().toLowerCase().contains(q.toLowerCase()))
                    .collect(Collectors.toList());
        }

        categories.sort((c1, c2) -> {
            if ("desc".equalsIgnoreCase(type)) {
                return c2.getTitle().compareTo(c1.getTitle());
            } else {
                return c1.getTitle().compareTo(c2.getTitle());
            }
        });

        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, categories.size());
        return ResponseEntity.ok(categories.subList(startIndex, endIndex));
    }

    //C05
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public ResponseMessage<CategoryResponse> updateCategoryWithId(@PathVariable("id") Long id,@RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.updateCategoryWithId(id,categoryRequest);
    }

}


