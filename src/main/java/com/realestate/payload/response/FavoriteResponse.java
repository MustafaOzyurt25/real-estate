package com.realestate.payload.response;

import com.realestate.entity.Advert;
import com.realestate.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FavoriteResponse {

    private Long favoriteId;
    private LocalDateTime createAt;
    private Advert advert;
    private User user;
}
