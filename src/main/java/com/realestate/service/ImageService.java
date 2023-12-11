package com.realestate.service;

import com.realestate.entity.Image;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.AdvertRepository;
import com.realestate.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public List<Image> saveAndGetImages(List<MultipartFile> images) {
        List<Image> realImages = new ArrayList<>();
        for(MultipartFile mP : images){
            try {
                byte[] imageByte = mP.getBytes();
                Image image = imageRepository.save(Image.builder().data(imageByte).build());
                realImages.add(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return realImages;
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
