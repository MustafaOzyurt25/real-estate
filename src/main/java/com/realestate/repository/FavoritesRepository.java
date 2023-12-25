package com.realestate.repository;

import com.realestate.entity.Advert;
import com.realestate.entity.Favorite;
import com.realestate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorite,Long> {


    @Transactional
    @Modifying
    void deleteByUserId(Long userId);

    Favorite findByUserAndAdvert(User user, Advert advert);

    List<Favorite> findByUser(User user);

    List<Favorite> findByUserId(Long id);

    List<Favorite> findAllByUserId(Long id);
}
