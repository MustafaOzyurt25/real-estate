package com.realestate.service;

import com.realestate.entity.Category;
import com.realestate.entity.CategoryPropertyKey;
import com.realestate.entity.User;
import com.realestate.exception.BadRequestException;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.helper.PageableHelper;
import com.realestate.payload.mappers.CategoryMapper;
import com.realestate.payload.mappers.CategoryPropertyKeyMapper;
import com.realestate.payload.request.CategoryPropertyKeyRequest;
import com.realestate.payload.request.CategoryRequest;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.CategoryResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.CategoryPropertyKeyRepository;
import com.realestate.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryPropertyKeyService categoryPropertyKeyService;
    private final PageableHelper pageableHelper;
    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;

    private final CategoryPropertyKeyMapper categoryPropertyKeyMapper;

    public ResponseMessage<CategoryResponse> deleteCategory(Long categoryId) {

        try {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                    new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND, categoryId)));
            if (category.getBuiltIn()) {
                throw new BadRequestException("Built-in categories cannot be deleted.");
            }
            if (categoryRepository.existsByIdAndAdvertsIsNotEmpty(categoryId)) {
                throw new RuntimeException("The category cannot be deleted because there are associated records.");
            }
            categoryRepository.delete(category);
            return ResponseMessage.<CategoryResponse>builder().
                    httpStatus(HttpStatus.OK).
                    message(SuccessMessages.DELETE_CATEGORY).
                    build();

        } catch (BadRequestException e) {
            throw new BadRequestException("Built-in categories cannot be deleted.");
        } catch (RuntimeException e) {
            throw new RuntimeException("The category cannot be deleted because there are associated records.");
        }


    }

    public Category createCategory(CategoryRequest categoryRequest) {

        String slugFromTitle = categoryRequest.getTitle().toLowerCase().replaceAll("\\s", "-").replaceAll("[^a-z0-9-]", "");
        String slug = categoryRequest.getSlug();

        if (slugFromTitle.equals(slug)) {
            Category category = categoryMapper.mapCategoryRequestToCategory(categoryRequest);
            List<CategoryPropertyKeyRequest> categoryPropertyKeyList = categoryRequest.getCategoryPropertyKeyList();

            category.setBuiltIn(false);

            categoryRepository.save(category);
            Long categoryId = category.getId();
            Category savedCategory = categoryPropertyKeyService.isCategoryExist(categoryId);

            if(categoryRequest.getCategoryPropertyKeyList() != null){
                List<CategoryPropertyKey> categoryPropertyKeyList1 = categoryPropertyKeyMapper.mapCategoryPropertyKeyRequestToListCategoryPropertyKey(categoryPropertyKeyList,savedCategory);

                for(CategoryPropertyKey categoryPropertyKey  : categoryPropertyKeyList1){
                    categoryPropertyKey.setBuiltIn(false);
                    categoryPropertyKeyRepository.save(categoryPropertyKey);
                }
            }
            return category;

        } else {
            throw new ConflictException(ErrorMessages.SLUG_IS_NOT_IN_THE_DESIRED_FORMAT);
        }

    }


    public ResponseEntity<Map<String, Object>> getAllCategoriesByPage(String q, int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort.toLowerCase(), type.toLowerCase());

        if (q != null) {
            q = q.trim().toLowerCase().replaceAll("-", " ");
        }

        Page<CategoryResponse> categories = categoryRepository.getAllCategoriesByPage(q, pageable)
                .map(categoryMapper::mapCategoryToCategoryResponse);
        Map<String, Object> responseBody = new HashMap<>();

        if (categories.isEmpty()) {
            responseBody.put("message", ErrorMessages.CRITERIA_CATEGORY_NOT_FOUND_MESSAGE);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        responseBody.put("Message", SuccessMessages.CRITERIA_CATEGORY_FOUND);
        responseBody.put("Categories", categories);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryPropertyKeyService.isCategoryExist(id);

        return categoryMapper.mapCategoryToCategoryResponse(category);

    }


    public Page<CategoryResponse> getAllCategoriesByPageWithAdmin(String q, int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort,type);

        if (q != null) {
            q = q.trim().toLowerCase().replaceAll("-", " ");
        }

        return categoryRepository.getCategoriesByAdmin(q,pageable).map(categoryMapper::mapCategoryToCategoryResponse);


    }

    public ResponseMessage<CategoryResponse> updateCategoryWithId(Long id, CategoryRequest categoryRequest) {

        Category oldCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND, id)));

        if (oldCategory.getBuiltIn()) {
            throw new ConflictException(ErrorMessages.BUILTIN_CATEGORY_CANT_BE_UPDATED);
        }

        String slugFromTitle = categoryRequest.getTitle().toLowerCase().replaceAll("\\s", "-").replaceAll("[^a-z0-9-]", "");
        String slug = categoryRequest.getSlug();

        if (slugFromTitle.equals(slug)) {

            List<CategoryPropertyKey> categoryPropertyKeys = categoryPropertyKeyService.getCategoryPropertyKeyByCategoryPropertyKeyIdList(categoryRequest.getCategoryPropertiesKeyId());
            Category category = categoryMapper.mapCategoryRequestToUpdatedCategory(id, categoryRequest, categoryPropertyKeys);
            category.setCreateAt(oldCategory.getCreateAt());
            return ResponseMessage.<CategoryResponse>builder()
                    .object(categoryMapper.mapCategoryToCategoryResponse(categoryRepository.save(category)))
                    .httpStatus(HttpStatus.OK)
                    .message(SuccessMessages.CATEGORY_SUCCESSFULLY_UPDATED)
                    .build();
        } else {
            throw new ConflictException(ErrorMessages.SLUG_IS_NOT_IN_THE_DESIRED_FORMAT);
        }


    }
}
