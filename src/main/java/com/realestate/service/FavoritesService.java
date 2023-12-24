package com.realestate.service;


import com.realestate.entity.Advert;
import com.realestate.entity.Favorite;
import com.realestate.entity.User;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.FavoriteMapper;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.FavoriteResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.FavoritesRepository;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final AdvertService advertService;
    private final FavoriteMapper favoriteMapper;


    // UserService'den çağırılıyor
    public void deleteByUserId(Long userId)
    {
        favoritesRepository.deleteByUserId(userId);
    }

    // public ResponseMessage<AdvertResponse> addOrRemoveAdvertToFavorites(Long id, HttpServletRequest httpServletRequest) {
  //     String userEmail = (String) httpServletRequest.getAttribute("email");


    //K03
    public ResponseMessage<FavoriteResponse> addOrRemoveAdvertToFavorites(Long id, HttpServletRequest httpServletRequest) {
        String userEmail = (String) httpServletRequest.getAttribute("email");

        Advert advert = advertService.getAdvertById(id);
        User user = userRepository.findByEmailEquals(userEmail);

        Favorite existingFavorite = favoritesRepository.findByUserAndAdvert(user, advert);

        if (existingFavorite != null) {
            favoritesRepository.delete(existingFavorite);

            return ResponseMessage.<FavoriteResponse>builder()
                    .httpStatus(HttpStatus.OK)
                    .message(SuccessMessages.FAVORITE_REMOVE)
                    .object(favoriteMapper.mapToFavoriteToFavoriteResponse(existingFavorite))
                    .build();

        } else {

            Favorite newFavorite = new Favorite();
            newFavorite.setAdvert(advert);
            newFavorite.setUser(user);
            favoritesRepository.save(newFavorite);

            return ResponseMessage.<FavoriteResponse>builder()
                    .httpStatus(HttpStatus.OK)
                    .message(SuccessMessages.FAVORITE_ADDED)
                    .object(favoriteMapper.mapToFavoriteToFavoriteResponse(newFavorite))
                    .build();
        }
    }

    //K04
    public ResponseMessage deleteAuthenticatedCustomerAllFavorites(HttpServletRequest httpServletRequest) {

        String userEmail = (String) httpServletRequest.getAttribute("email");

        User user = userRepository.findByEmailEquals(userEmail);


        favoritesRepository.deleteAll(user.getFavorites());
        return ResponseMessage.builder()
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.ALL_FAVORITES_DELETED).build();

    }

    public ResponseEntity<List<FavoriteResponse>> getUsersFavorites(Long id) {

        return ResponseEntity.ok(favoritesRepository.findByUser(userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,id)))).stream().map(favoriteMapper::mapToFavoriteToFavoriteResponse).collect(Collectors.toList()));
    }


    //K01
    // public ResponseMessage<List<AdvertResponse>> getAuthenticatedCustomerAllFavorites(HttpServletRequest httpServletRequest) {
    //    String userEmail=(String) httpServletRequest.getAttribute("email");
    //    User user=userRepository.findByEmailEquals(userEmail);
    //    List<Favorite> favoriteList= favoritesRepository.findByUser(user);

    //  }


}
