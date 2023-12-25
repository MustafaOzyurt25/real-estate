package com.realestate.controller;


import com.realestate.payload.response.ImageResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.AdvertImageService;
import com.realestate.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final AdvertImageService advertImageService;
    private final ImageService imageService;

    @PostMapping("/{advertId}")
    public ResponseEntity<Map<String, Object>> addImageToAdvert(@PathVariable("advertId") Long advertId,
                                                                @ModelAttribute List<MultipartFile> imageFiles) {

        return advertImageService.addImageToAdvert(advertId, imageFiles);

    }

    @PutMapping("/{imageId}")
    //@PreAuthorize("hasAnyRole('CUSTOMER','MANAGER','ADMIN')")
    public ResponseMessage<ImageResponse> setFeaturedArea(@PathVariable Long imageId) {
        return imageService.setFeaturedArea(imageId);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteImagesById(@PathVariable List<Long> ids){
        return imageService.deleteImagesById(ids);

    }


    @GetMapping("/{imageId}")
    public ImageResponse getImageAnAdvert(@PathVariable("imageId") Long imageId)
    {
        return imageService.getImageAnAdvert(imageId);
    }
}
