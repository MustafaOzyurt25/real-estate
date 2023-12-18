package com.realestate.service;

import com.realestate.entity.Category;
import com.realestate.entity.CategoryPropertyKey;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.CategoryMapper;
import com.realestate.payload.request.CategoryRequest;
import com.realestate.payload.response.CategoryResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.CategoryRepository;
import com.realestate.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryPropertyKeyService categoryPropertyKeyService;
    private final AdvertService advertService;
    private final PageableHelper pageableHelper;

    public ResponseMessage<CategoryResponse> deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND,categoryId)));
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

    public Category createCategory(CategoryRequest categoryRequest){

        List<CategoryPropertyKey> categoryPropertyKeys = categoryPropertyKeyService.getCategoryPropertyKeyByCategoryPropertyKeyIdList(categoryRequest.getCategoryPropertiesKeyId());

        String slug = categoryRequest.getTitle().toLowerCase().replaceAll("\\s", "-").replaceAll("[^a-z0-9-]", "");

        Category category = categoryMapper.mapCategoryRequestToCategory(categoryRequest,categoryPropertyKeys);

        category.setBuiltIn(false);
        category.setIsActive(true);

        categoryRepository.save(category);
        return category;
    }


    public Page<CategoryResponse> getAllCategoriesByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return categoryRepository.findAll(pageable).map(categoryMapper::mapCategoryToCategoryResponse);
    }

    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryPropertyKeyService.isCategoryExist(id);

        return categoryMapper.mapCategoryToCategoryResponse(category);

    }
}
