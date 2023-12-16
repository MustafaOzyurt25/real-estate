package com.realestate.payload.response;

import com.realestate.entity.CategoryPropertyKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryResponse {

    private Long categoryId;
    private String title;
    private String icon;
    private Integer seq;
    private String slug;
    private Boolean isActive;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<CategoryPropertyKey> categoryPropertyKeys;


}
