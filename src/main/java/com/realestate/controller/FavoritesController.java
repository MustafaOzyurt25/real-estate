package com.realestate.controller;

import com.realestate.payload.response.FavoriteResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.FavoritesService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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

    //K06
    @DeleteMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseMessage deleteFavoritesIdByAdminOrManager(@PathVariable("id")  Long id){
       return favoritesService.deleteFavoritesIdByAdminOrManager (id);
    }
}