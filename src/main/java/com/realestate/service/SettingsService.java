package com.realestate.service;

import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingsService
{
    private final AdvertRepository advertRepository;
    private final AdvertTypeRepository advertTypeRepository;
    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final TourRequestsRepository tourRequestsRepository;
    private final UserRepository userRepository;

    public ResponseMessage dataBaseReset()
    {
        if(!advertTypeRepository.isEmpty()) // true ise silinecek data vardır
        {
            advertTypeRepository.deleteAll();
        }

        if(!advertRepository.isEmpty())
        {
            advertRepository.deleteAdverts();
        }

        if(!categoryRepository.isEmpty())
        {
            categoryRepository.deleteCategories();
        }

        if(!categoryPropertyKeyRepository.isEmpty())
        {
            categoryPropertyKeyRepository.deleteCategoryPropertyKeys();
        }

        if(!imageRepository.isEmpty())
        {
            imageRepository.deleteAll();
        }

        if(!tourRequestsRepository.isEmpty())
        {
            tourRequestsRepository.deleteAll();
        }

        if(!userRepository.isEmpty())
        {
            userRepository.deleteUsers();
        }




        return ResponseMessage.builder() // !gözden geçirilecek. Daha açıklayıcı mesaj verilebilir.
                .message("Başarıyle silindi")
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
