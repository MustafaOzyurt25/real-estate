package com.realestate.controller;

import com.realestate.service.AdvertImageService;
import com.realestate.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final AdvertImageService advertImageService;
    private final ImageService imageService;

    @PostMapping("/{advertId}")
    public List<Long> addImageToAdvert(@PathVariable("advertId") Long advertId,
                                       @ModelAttribute List<MultipartFile> imageFiles) {

        return advertImageService.addImageToAdvert(advertId, imageFiles);

    }

    @PutMapping("/imageId")
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER','ADMIN')")
    public ResponseEntity<String> setFeaturedArea(@PathVariable Long imageId) {
        return imageService.setFeaturedArea();
}
