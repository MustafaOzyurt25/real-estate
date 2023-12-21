package com.realestate.controller;


import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")

public class FavoritesController {
    private final FavoritesService favoritesService;

  // @PostMapping("/{id}/auth")

  // public ResponseMessage<AdvertResponse> addOrRemoveAdvertToFavorites(@PathVariable Long id , HttpServletRequest httpServletRequest){
  //     return favoritesService.addOrRemoveAdvertToFavorites(id,httpServletRequest);
  // }

}
