package com.realestate.service;


import com.realestate.entity.Category;
import com.realestate.entity.CategoryPropertyKey;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.payload.mappers.CategoryPropertyKeyMapper;
import com.realestate.payload.request.CategoryPropertyKeyRequest;
import com.realestate.repository.CategoryPropertyKeyRepository;
import com.realestate.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryPropertyKeyService {


    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private final CategoryPropertyKeyMapper categoryPropertyKeyMapper;
    private final CategoryRepository categoryRepository;

    // Not: Save() ******************************************************************
    public CategoryPropertyKey createProperty(Long categoryId, CategoryPropertyKeyRequest request) {

        // burda ; verilen categoryId ile bir category varmi kontrolu yaptim.
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND_EXCEPTION, categoryId)));


        CategoryPropertyKey categoryPropertyKey = categoryPropertyKeyMapper.mapCategoryPropertyKeyRequestToCategoryPropertyKey(request);
        categoryPropertyKey.setCategory(category);// mapperda bu field, eksik kalmisti!!

        return categoryPropertyKeyRepository.save(categoryPropertyKey);


    }

    public CategoryPropertyKey isCategoryPropertyKeyExistById(Long id) {

        return categoryPropertyKeyRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_CATEGORYPROPERTYKEY_MESSAGE, id)));
    }

    public List<CategoryPropertyKey> getCategoryPropertyKeyByCategoryPropertyKeyIdList(List<Long> idList) {

        return idList.stream()
                .map(this::isCategoryPropertyKeyExistById)
                .collect(Collectors.toList());
    }
}

