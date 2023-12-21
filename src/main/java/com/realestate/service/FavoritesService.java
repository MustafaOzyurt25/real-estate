package com.realestate.service;

import com.realestate.entity.Advert;
import com.realestate.entity.User;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.FavoritesRepository;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final AdvertService advertService;

  // public ResponseMessage<AdvertResponse> addOrRemoveAdvertToFavorites(Long id, HttpServletRequest httpServletRequest) {
  //     String userEmail = (String) httpServletRequest.getAttribute("email");

  //     Advert advert=advertService.getAdvertById(id);
  //     User user=userRepository.findByEmailEquals(userEmail);

  //     user.getFavorites().add();

  //

  // }
}
