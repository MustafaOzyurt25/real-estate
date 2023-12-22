package com.realestate.payload.mappers;

import com.realestate.entity.Favorite;
import com.realestate.payload.response.FavoriteResponse;
import org.springframework.stereotype.Component;

@Component
public class FavoriteMapper {
    public FavoriteResponse mapToFavoriteToFavoriteResponse(Favorite favorite){
        return FavoriteResponse.builder()
                .favoriteId(favorite.getId())
                .createAt(favorite.getCreateAt())
                .advert(favorite.getAdvert())
                .user(favorite.getUser()).build();
    }
}
