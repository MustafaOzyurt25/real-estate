package com.realestate.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryRequest {

    @NotNull
    @Size(min=5,max=150,message = "The number of characters in the title must be between 5 and 150")
    private String title;

    @NotNull
    private String icon;

    @NotNull
    private Integer seq;

    @Column(unique = true)
    private String slug;

    @NotNull
    private Boolean isActive;

    
//  @NotNull 
    private List<Long> categoryPropertiesKeyId;

    private LocalDateTime createAt = LocalDateTime.now();
}
