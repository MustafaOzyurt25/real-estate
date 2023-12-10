package com.realestate.payload.mappers;

import com.realestate.entity.Image;
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


}
