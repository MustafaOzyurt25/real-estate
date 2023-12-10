package com.realestate.payload.request;


import com.realestate.entity.Image;
import com.realestate.entity.enums.AdvertStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvertRequest {
    @NotNull
    @Size(min=5,max=150,message = "The number of characters in the title must be between 5 and 150")
    private String title;

    @Size(max=300,message = "The number of characters in the description max 300")
    private String description;

    @NotNull
    @Size(min=5,max=150,message = "The number of characters in the title must be between 5 and 200")
    private String slug;

    @NotNull
    private Double price;

    @NotNull
    private String location;
    private List<MultipartFile> images;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
}