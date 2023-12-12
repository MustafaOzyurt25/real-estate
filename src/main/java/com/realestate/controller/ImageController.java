package com.realestate.controller;

import com.realestate.entity.Image;
import com.realestate.service.AdvertImageService;
import com.realestate.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final AdvertImageService advertImageService;

    @PostMapping("/{advertId}")
    public List<Long> addImageToAdvert(@PathVariable("advertId") Long advertId,
                                        @ModelAttribute List<MultipartFile> imageFiles){

        return advertImageService.addImageToAdvert(advertId,imageFiles);

    }

    @GetMapping("/{imageId}")
    public Image getImageAnAdvert(@PathVariable("advertId") Long advertId)
    {
        return advertImageService.getImageAnAdvert(advertId);
    }
}
