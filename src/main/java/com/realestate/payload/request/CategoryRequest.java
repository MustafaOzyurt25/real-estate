package com.realestate.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(min=5,max=150,message = "The number of characters in the title must be between 5 and 200")
    private String slug;

    @NotNull
    private String icon;

    @NotNull
    private Integer seq;

    @NotNull
    private Boolean is_active;

    @NotNull
    private List<Long> categoryPropertiesKeyId;
}
