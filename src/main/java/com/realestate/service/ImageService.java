package com.realestate.service;

import com.realestate.entity.Image;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.payload.mappers.ImageMapper;
import com.realestate.payload.response.ImageResponse;
import com.realestate.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ResponseEntity<String> setFeaturedArea(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        setOtherImagesAsNotFeatured(imageId);
        image.setFeatured(true);
        imageRepository.save(image);
        return null;
    }

    private void setOtherImagesAsNotFeatured(Long currentImageId) {
        List<Image> allImages = imageRepository.findAll();

        for (Image img : allImages) {
            if (!img.getId().equals(currentImageId)) {
                img.setFeatured(false);
                imageRepository.save(img);
            }
        }
    }

    public List<Image> saveAndGetImages(List<MultipartFile> images) {
        List<Image> realImages = new ArrayList<>();
        if(images==null)
        {

        }
        else {
            for(MultipartFile mP : images){
                try {
                    byte[] imageByte = mP.getBytes();
                    Image image = imageRepository.save(Image.builder().data(imageByte).build());
                    realImages.add(image);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return realImages;
    }

    public ImageResponse getImageAnAdvert(Long imageId)
    {
        if(!imageRepository.existsById(imageId))
        {
            throw new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND_EXCEPTION,"image"));
        }

        Image image = imageRepository.findById(imageId).get();
        ImageResponse returnImageObject = imageMapper.getImageResponseFromImage(image);
        return returnImageObject;
    }
}
