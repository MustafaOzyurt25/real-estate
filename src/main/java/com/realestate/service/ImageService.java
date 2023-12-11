package com.realestate.service;

import com.realestate.entity.Image;
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

}
