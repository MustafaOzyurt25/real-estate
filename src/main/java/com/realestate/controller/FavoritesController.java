package com.realestate.controller;


import com.realestate.entity.Favorite;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.FavoriteResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.FavoritesService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")

public class FavoritesController {
    private final FavoritesService favoritesService;


    //K03
   @PostMapping("/{id}/auth")
   @PreAuthorize("hasAnyAuthority('CUSTOMER')")

   public ResponseMessage<FavoriteResponse> addOrRemoveAdvertToFavorites(@PathVariable Long id , HttpServletRequest httpServletRequest){
       return favoritesService.addOrRemoveAdvertToFavorites(id,httpServletRequest);
  }


  //K04

    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @DeleteMapping("/auth")

    public ResponseMessage deleteAuthenticatedCustomerAllFavorites(HttpServletRequest httpServletRequest){
       return favoritesService.deleteAuthenticatedCustomerAllFavorites(httpServletRequest);
    }

    //K01
  //  @PreAuthorize("hasAnyAuthority('CUSTOMER')")
  //  @GetMapping("/auth")
  //  public ResponseMessage<List<AdvertResponse>> getAuthenticatedCustomerAllFavorites(HttpServletRequest httpServletRequest){
  //     return favoritesService.getAuthenticatedCustomerAllFavorites(httpServletRequest);
  //  }
}