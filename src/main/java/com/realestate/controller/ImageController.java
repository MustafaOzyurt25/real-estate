package com.realestate.controller;


import com.realestate.payload.response.ImageResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.AdvertImageService;
import com.realestate.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //I02
    @PreAuthorize("hasAnyAuthority('CUSTOMER','ADMIN','MANAGER')")
    @PostMapping("/{advertId}")
    public ResponseEntity<Map<String, Object>> addImageToAdvert(@PathVariable("advertId") Long advertId,
                                                                @ModelAttribute List<MultipartFile> imageFiles) {

        return advertImageService.addImageToAdvert(advertId, imageFiles);

    }




    //I04
    @PutMapping("/{imageId}")
    //@PreAuthorize("hasAnyRole('CUSTOMER','MANAGER','ADMIN')")
    public ResponseMessage<ImageResponse> setFeaturedArea(@PathVariable Long imageId) {
        return imageService.setFeaturedArea(imageId);
    }



    //I03
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteImagesById(@PathVariable List<Long> ids){
        return imageService.deleteImagesById(ids);

    }


    //I01
    @GetMapping("/{imageId}")
    public ImageResponse getImageAnAdvert(@PathVariable("imageId") Long imageId)
    {
        return imageService.getImageAnAdvert(imageId);
    }
}
