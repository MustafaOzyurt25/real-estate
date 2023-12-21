package com.realestate.controller;


import com.realestate.service.FavoritesService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




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