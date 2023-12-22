package com.realestate.service;


import com.realestate.repository.FavoritesRepository;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final AdvertService advertService;

    // UserService'den çağırılıyor
    public void deleteByUserId(Long userId)
    {
        favoritesRepository.deleteByUserId(userId);
    }

    // public ResponseMessage<AdvertResponse> addOrRemoveAdvertToFavorites(Long id, HttpServletRequest httpServletRequest) {
  //     String userEmail = (String) httpServletRequest.getAttribute("email");

  //     Advert advert=advertService.getAdvertById(id);
  //     User user=userRepository.findByEmailEquals(userEmail);

  //     user.getFavorites().add();

  //

  // }
}
