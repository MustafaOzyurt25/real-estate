package com.realestate.service;

import com.realestate.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertImageService {
    private final ImageService imageService;
    private final AdvertService advertService;
    public List<Long> addImageToAdvert(Long advertId, List<MultipartFile> imageFiles) {

        List<Image> images = imageService.saveAndGetImages(imageFiles);
        advertService.addImageToAdvert(advertId,images);
        return images.stream().map(Image::getId).collect(Collectors.toList());
    }


    public Image getImageAnAdvert(Long advertId)
    {
        Image img =
    }


}
