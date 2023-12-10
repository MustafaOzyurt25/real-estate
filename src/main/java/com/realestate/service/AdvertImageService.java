package com.realestate.service;

import com.realestate.entity.Image;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertImageService {
    private final ImageService imageService;
    private final AdvertService advertService;
    private  final ImageRepository imageRepository;
    public List<Long> addImageToAdvert(Long advertId, List<MultipartFile> imageFiles) {

        List<Image> images = imageService.saveAndGetImages(imageFiles);
        advertService.addImageToAdvert(advertId,images);
        return images.stream().map(Image::getId).collect(Collectors.toList());
    }

    public ResponseMessage deleteImagesById(List<Long> id) {
        id.forEach(this::isImageExist);
        imageRepository.deleteAllById(id);

        return ResponseMessage.builder()
                .message(SuccessMessages.IMAGE_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Image isImageExist(Long imageId){

        return imageRepository.findById(imageId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_IMAGE_MESSAGE,imageId)));
    }

}
