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

        // burda ; verilen categoryId ile bir category varmi kontrolu yaptim.eger yoksa excptn firlatir.
        Category category = isCategoryExist(categoryId);


        CategoryPropertyKey categoryPropertyKey = categoryPropertyKeyMapper.mapCategoryPropertyKeyRequestToCategoryPropertyKey(request);
        categoryPropertyKey.setCategory(category);// mapperda bu field, eksik kalmisti!!

        return categoryPropertyKeyRepository.save(categoryPropertyKey);


    }


    // Not: getCategoryPropertyKeysByCategoryId() ******************************************************************

    public ResponseMessage<List<CategoryPropertyKey>> getCategoryPropertyKeysByCategoryId(Long categoryId) {
        // 1- bu categoryId ile kayitli bir Category var mi ?
        Category category = isCategoryExist(categoryId);  // buna gerek varmi. once category var mi kontrolu yapmaya.
        // todo : bu if-else ifadesi daha sade yazilabilir mi? burda isExistsCategoryPropertyKeyByCategoryId metodu yazilip daha sadelestirilebilir gibi.
        if (categoryPropertyKeyRepository.existsByCategoryId(categoryId)) {  // burda categoryId ile propertyKey kontrolu
            List<CategoryPropertyKey> categoryPropertyKeys = categoryPropertyKeyRepository.findByCategoryId(categoryId);
            return ResponseMessage.<List<CategoryPropertyKey>>builder()
                    .object(categoryPropertyKeys)
                    .message(SuccessMessages.CATEGORY_PROPERTY_KEY_FOUND)
                    .httpStatus(HttpStatus.OK)
                    .build();

        } else {
            return ResponseMessage.<List<CategoryPropertyKey>>builder()  // obje yok ama sadece mesaj vermek istedim. 
                    .message(ErrorMessages.CATEGORY_PROPERTY_KEY_NOT_FOUND_MESSAGE)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }


    }

    // Not: updateByCategoryPropertyKeyId() *********************************************************************

    public ResponseMessage<CategoryPropertyKey> updateCategoryPropertyKeyById(Long propertyKeyId, CategoryPropertyKeyRequest request) {

        //1. verilen id ile kayitli bir CategoryPropertyKey objesi varmi?   
        CategoryPropertyKey existingCategoryPropertyKey = isCategoryPropertyKeyExist(propertyKeyId);// eger yoksa exception firlar, varsa devam...

        //2.  eger mevcut propety'nin builtIn property si  true ise update edilemez ( requirements )
        if (existingCategoryPropertyKey.getBuilt_in()) {
            throw new ConflictException(ErrorMessages.THE_PROPERTY_KEY_CAN_NOT_BE_UPDATED);
        }
        // 3. todo: deleteCategoryPropertyKeyById(id); // eger  varsa sil cunku onu guncelleyecem mantikli mi!!!

        // 4.kullaniciya updated property key object olmali ( requirements )
        CategoryPropertyKey categoryPropertyKey = categoryPropertyKeyMapper
                .mapCategoryPropertyKeyRequestoUpdatedCategoryPropertyKey(propertyKeyId, request);

        Category category = isCategoryExist(request.getCategory_id());
        categoryPropertyKey.setCategory(category); // mapperda bu field, eksik kalmisti!!

        CategoryPropertyKey propertyKeyUpdated = categoryPropertyKeyRepository.save(categoryPropertyKey);


        return ResponseMessage.<CategoryPropertyKey>builder()
                .object(propertyKeyUpdated) // updated property key object istenmis cunku.
                .message(SuccessMessages.CATEGORY_PROPERTY_KEY_UPDATE)
                .httpStatus(HttpStatus.OK).build();

    }

    //verilen categoryPropertyKeyId ile kayitli bir CategoryPropertyKey objesi varmi?
    public CategoryPropertyKey isCategoryPropertyKeyExist(Long id) {
        return categoryPropertyKeyRepository.
                findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.CATEGORY_PROPERTY_KEY_NOT_FOUND_MESSAGE, id)));

    }
 

    public Category isCategoryExist(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.CATAGORY_NOT_FOUND_MESSAGE, categoryId)));
    }


    

    public CategoryPropertyKey isCategoryPropertyKeyExistById(Long id) {

        return categoryPropertyKeyRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_CATEGORY_PROPERTY_KEY_MESSAGE, id)));
    }

    public List<CategoryPropertyKey> getCategoryPropertyKeyByCategoryPropertyKeyIdList(List<Long> idList) {

        return idList.stream()
                .map(this::isCategoryPropertyKeyExistById)
                .collect(Collectors.toList());
    }


} // service

