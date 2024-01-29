package com.realestate.service;

import com.realestate.entity.Category;
import com.realestate.entity.CategoryPropertyKey;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.helper.PageableHelper;
import com.realestate.payload.mappers.CategoryMapper;
import com.realestate.payload.request.CategoryRequest;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.CategoryResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryPropertyKeyService categoryPropertyKeyService;
    private final AdvertService advertService;
    private final PageableHelper pageableHelper;

    public ResponseMessage<CategoryResponse> deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND, categoryId)));
        if (category.getBuiltIn()) {
            throw new RuntimeException("Built-in categories cannot be deleted.");
        }
        if (categoryRepository.existsByIdAndAdvertsIsNotEmpty(categoryId)) {
            throw new RuntimeException("The category cannot be deleted because there are associated records.");
        }
        categoryRepository.delete(category);
        return ResponseMessage.<CategoryResponse>builder().
                httpStatus(HttpStatus.OK).
                message(SuccessMessages.DELETE_CATEGORY).
                build();
    }

    //C04
    public Category createCategory(CategoryRequest categoryRequest) {

        List<CategoryPropertyKey> categoryPropertyKeys = categoryPropertyKeyService.getCategoryPropertyKeyByCategoryPropertyKeyIdList(categoryRequest.getCategoryPropertiesKeyId());

        String slug = categoryRequest.getTitle().toLowerCase().replaceAll("\\s", "-").replaceAll("[^a-z0-9-]", "");

        Category category = categoryMapper.mapCategoryRequestToCategory(categoryRequest, categoryPropertyKeys);

        category.setBuiltIn(false);
        category.setIsActive(true);


        categoryRepository.save(category);

        return category;

    }




    public ResponseEntity<Map<String, Object>> getAllCategoriesByPage(String q, int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page,size,sort.toLowerCase(),type.toLowerCase());

        if(q!=null){
            q=q.trim().toLowerCase().replaceAll("-"," ");
        }

        Page<CategoryResponse> categories = categoryRepository.getAllCategoriesByPage(q,pageable)
                .map(categoryMapper::mapCategoryToCategoryResponse);
        Map<String, Object> responseBody = new HashMap<>();

        if (categories.isEmpty()){
            responseBody.put("message", ErrorMessages.CRITERIA_CATEGORY_NOT_FOUND_MESSAGE);
            return new ResponseEntity<>(responseBody,HttpStatus.OK);
        }
        responseBody.put("Message",SuccessMessages.CRITERIA_CATEGORY_FOUND);
        responseBody.put("Categories",categories);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }





    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryPropertyKeyService.isCategoryExist(id);

        return categoryMapper.mapCategoryToCategoryResponse(category);

    }


    public List<Category> getAllCategories(String q, int page, int size, String sort, String type) {
      return categoryRepository.findAll();
    }

    public ResponseMessage<CategoryResponse> updateCategoryWithId(Long id,CategoryRequest categoryRequest) {
        Category oldCategory = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND,id)));
        if (oldCategory.getBuiltIn()){
            throw new ConflictException(ErrorMessages.BUILTIN_CATEGORY_CANT_BE_UPDATED);
        }
        List<CategoryPropertyKey> categoryPropertyKeys = categoryPropertyKeyService.getCategoryPropertyKeyByCategoryPropertyKeyIdList(categoryRequest.getCategoryPropertiesKeyId());
        Category category = categoryMapper.mapCategoryRequestToUpdatedCategory(id,categoryRequest,categoryPropertyKeys);
        category.setCreateAt(oldCategory.getCreateAt());
        return ResponseMessage.<CategoryResponse>builder()
                .object(categoryMapper.mapCategoryToCategoryResponse(categoryRepository.save(category)))
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.CATEGORY_SUCCESSFULLY_UPDATED)
                .build();
    }
}
