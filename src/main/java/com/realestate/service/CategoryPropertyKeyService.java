package com.realestate.service;


import com.realestate.entity.Category;
import com.realestate.entity.CategoryPropertyKey;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.CategoryPropertyKeyMapper;
import com.realestate.payload.request.CategoryPropertyKeyRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.CategoryPropertyKeyRepository;
import com.realestate.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryPropertyKeyService {


    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private final CategoryPropertyKeyMapper categoryPropertyKeyMapper;
    private final CategoryRepository categoryRepository;
    
    public ResponseMessage<CategoryPropertyKey> createProperty(Long categoryId, CategoryPropertyKeyRequest request) {
        Category category = isCategoryExist(categoryId);

        CategoryPropertyKey categoryPropertyKey = categoryPropertyKeyMapper.mapCategoryPropertyKeyRequestToCategoryPropertyKey(request);
        categoryPropertyKey.setCategory(category);
        categoryPropertyKey.setBuiltIn(true);

        CategoryPropertyKey savedCategoryPropertyKey = categoryPropertyKeyRepository.save(categoryPropertyKey);
        return ResponseMessage.<CategoryPropertyKey>builder()
                .object(savedCategoryPropertyKey)
                .httpStatus(HttpStatus.CREATED)
                .message(SuccessMessages.CATEGORY_PROPERTY_KEY_CREATE)
                .build();


    }
    
    public ResponseMessage<List<CategoryPropertyKey>> getCategoryPropertyKeysByCategoryId(Long categoryId) {
        isCategoryExist(categoryId);

        List<CategoryPropertyKey> categoryPropertyKeys = getCategoryPropertyKeys(categoryId);

        HttpStatus httpStatus = categoryPropertyKeys.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        String message = categoryPropertyKeys.isEmpty() ?
                ErrorMessages.CATEGORY_PROPERTY_KEY_NOT_FOUND_MESSAGE :
                SuccessMessages.CATEGORY_PROPERTY_KEY_FOUND;

        return ResponseMessage.<List<CategoryPropertyKey>>builder()
                .object(categoryPropertyKeys)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }

    private List<CategoryPropertyKey> getCategoryPropertyKeys(Long categoryId) {
        return categoryPropertyKeyRepository.existsByCategoryId(categoryId) ?
                categoryPropertyKeyRepository.findByCategoryId(categoryId) :
                Collections.emptyList();
    }
    
    public ResponseMessage<CategoryPropertyKey> updateCategoryPropertyKeyById(Long propertyKeyId, CategoryPropertyKeyRequest request) {
        CategoryPropertyKey existingCategoryPropertyKey = isCategoryPropertyKeyExistById(propertyKeyId);
        if (existingCategoryPropertyKey.getBuiltIn()) {
            throw new ConflictException(ErrorMessages.THE_PROPERTY_KEY_CAN_NOT_BE_UPDATED);
        }
        CategoryPropertyKey categoryPropertyKey = categoryPropertyKeyMapper
                .mapCategoryPropertyKeyRequestoUpdatedCategoryPropertyKey(propertyKeyId, request);

        Category category = isCategoryExist(existingCategoryPropertyKey.getCategory().getId());
        categoryPropertyKey.setCategory(category);
        categoryPropertyKey.setBuiltIn(false);

        CategoryPropertyKey propertyKeyUpdated = categoryPropertyKeyRepository.save(categoryPropertyKey);
        return ResponseMessage.<CategoryPropertyKey>builder()
                .object(propertyKeyUpdated)
                .message(SuccessMessages.CATEGORY_PROPERTY_KEY_UPDATE)
                .httpStatus(HttpStatus.OK).build();

    }
    
    public Category isCategoryExist(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND_MESSAGE, categoryId)));
    }
    
    public ResponseMessage<CategoryPropertyKey> deleteCategoryPropertyKeyByKeyId(Long id) {
        CategoryPropertyKey categoryPropertyKey = isCategoryPropertyKeyExistById(id);

        if (categoryPropertyKey.getBuiltIn()) {
            throw new RuntimeException(ErrorMessages.THE_PROPERTY_KEY_CAN_NOT_BE_DELETED);
        }
        categoryPropertyKeyRepository.delete(categoryPropertyKey);
        return ResponseMessage.<CategoryPropertyKey>builder().
                object(categoryPropertyKey).
                httpStatus(HttpStatus.OK).
                message(SuccessMessages.CATEGORY_PROPERTY_KEY_DELETED).
                build();


    }


    public CategoryPropertyKey isCategoryPropertyKeyExistById(Long id) {

        return categoryPropertyKeyRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_CATEGORY_PROPERTY_KEY_MESSAGE, id)));
    }

    public List<CategoryPropertyKey> getCategoryPropertyKeyByCategoryPropertyKeyIdList(List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList(); 
        }
        
        return idList.stream()
                .map(this::isCategoryPropertyKeyExistById)
                .collect(Collectors.toList());
    }


} 

