package com.realestate.repository;

import com.realestate.entity.Advert;
import com.realestate.entity.Favorite;
import com.realestate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorite,Long> {
    Favorite findByUserAndAdvert(User user, Advert advert);

    List<Favorite> findByUser(User user);
}
