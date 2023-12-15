package com.realestate.service;

import com.realestate.entity.Image;
import com.realestate.exception.ConflictException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AdvertImageService {
    private final ImageService imageService;
    private final AdvertService advertService;
    public ResponseEntity<Map<String,Object>> addImageToAdvert(Long advertId, List<MultipartFile> imageFiles) {
        Map<String, Object> responseBody = new HashMap<>();
        try {
            if (imageFiles==null||imageFiles.get(0).getBytes().length<1) {
                responseBody.put("message", ErrorMessages.PLEASE_SEND_IMAGE);
                return new ResponseEntity<>(responseBody,HttpStatus.OK);
            }
        } catch (IOException e) {
            throw new ConflictException(e.getMessage());
        }
        List<Image> images = imageService.saveAndGetImages(imageFiles);
        advertService.addImageToAdvert(advertId,images);
        responseBody.put("IDs of the pictures in the advert : ",images.stream().map(Image::getId).toList());
        responseBody.put("message", SuccessMessages.IMAGES_SUCCESSFULLY_ADDED_TO_ADVERT);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }


}
