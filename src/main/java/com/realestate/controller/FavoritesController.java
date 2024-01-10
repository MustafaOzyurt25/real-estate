package com.realestate.controller;

import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.FavoriteResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseMessage<FavoriteResponse> addOrRemoveAdvertToFavorites(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return favoritesService.addOrRemoveAdvertToFavorites(id, httpServletRequest);
    }


    //K04


    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @DeleteMapping("/auth")
    public ResponseMessage deleteAuthenticatedCustomerAllFavorites(HttpServletRequest httpServletRequest) {
        return favoritesService.deleteAuthenticatedCustomerAllFavorites(httpServletRequest);
    }

    //K05
    @PreAuthorize("hasAnyAuthority('ADMIN' , 'MANAGER')")
    @DeleteMapping("/admin/{id}")
    public void deleteAllFavoritesOfAUser(@PathVariable("id") Long userId) {
        favoritesService.deleteByUserId(userId);
    }

  

    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @GetMapping("/auth")
    public ResponseMessage<List<AdvertResponse>> getAuthenticatedCustomerAllFavorites(HttpServletRequest httpServletRequest){
       return favoritesService.getAuthenticatedCustomerAllFavorites(httpServletRequest);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/admin/{id}")
    public ResponseEntity<List<FavoriteResponse>> getUsersFavorites(@PathVariable("id") Long id){
       return favoritesService.getUsersFavorites(id);
    }


    //K06
    @DeleteMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseMessage deleteFavoritesIdByAdminOrManager(@PathVariable("id")  Long id){
       return favoritesService.deleteFavoritesIdByAdminOrManager (id);
    }

}