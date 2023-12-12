package com.realestate.payload.mappers;

import com.realestate.entity.Image;
import com.realestate.payload.response.ImageResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImageMapper {

    public Image multipartFileToByteArray(MultipartFile multipartFile) {

        byte[] data;

        try{
            data = multipartFile.getBytes();
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return Image.builder()
                .name(multipartFile.getName())
                .type(multipartFile.getContentType())
                .data(data)
                .build();
    }

    public ImageResponse getImageResponseFromImage(Image image)
    {
        return ImageResponse.builder()
                .name(image.getName())
                .type(image.getType())
                .featured(image.getFeatured())
                .data(image.getData())
                .build();
    }


}
