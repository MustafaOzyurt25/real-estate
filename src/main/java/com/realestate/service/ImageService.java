package com.realestate.service;

import com.realestate.entity.Image;
import com.realestate.payload.mappers.ImageMapper;
import com.realestate.payload.request.ImageRequest;
import com.realestate.payload.response.ImageResponse;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.AdvertRepository;
import com.realestate.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ImageMapper imageMapper;

    public ResponseMessage<ImageResponse> setFeaturedArea(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        List<Image> allImages = imageRepository.findAll();
        for (Image img : allImages) {
            img.setFeatured(img.getId().equals(imageId));
            imageRepository.save(img);
        }

        return ResponseMessage.success(convertToImageResponse(image));
    }

    public ResponseMessage<ImageResponse> createImage(ImageRequest imageRequest) {
        Image image = new Image();
        image.setData(imageRequest.getData());
        image.setName(imageRequest.getName());
        image.setType(imageRequest.getType());
        image.setFeatured(imageRequest.getFeatured());

        Image savedImage = imageRepository.save(image);
        return ResponseMessage.success(convertToImageResponse(savedImage));
    }

    public List<ImageResponse> getAllImages() {
        List<Image> allImages = imageRepository.findAll();
        return allImages.stream()
                .map(this::convertToImageResponse)
                .collect(Collectors.toList());
    }

    public ImageResponse convertToImageResponse(Image image) {
        return ImageResponse.builder()
                .imageId(image.getId())
                .name(image.getName())
                .type(image.getType())
                .featured(image.getFeatured())
                .data(image.getData())
                .build();
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
