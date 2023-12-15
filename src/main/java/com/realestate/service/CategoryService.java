package com.realestate.service;

import com.realestate.entity.Advert;
import com.realestate.entity.Category;
import com.realestate.entity.CategoryPropertyKey;
import com.realestate.payload.mappers.CategoryMapper;
import com.realestate.payload.request.CategoryRequest;
import com.realestate.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryPropertyKeyService categoryPropertyKeyService;
    private final AdvertService advertService;


    public Category createCategory(CategoryRequest categoryRequest){

        List<CategoryPropertyKey> categoryPropertyKeys = categoryPropertyKeyService.getCategoryPropertyKeyByCategoryPropertyKeyIdList(categoryRequest.getCategoryPropertiesKeyId());

        String slug = categoryRequest.getTitle().toLowerCase().replaceAll("\\s", "-").replaceAll("[^a-z0-9-]", "");

        Category category = categoryMapper.mapCategoryRequestToCategory(categoryRequest,categoryPropertyKeys);

        category.setBuilt_in(false);
        category.setIs_active(true);

        categoryRepository.save(category);
        return category;
    }


}
