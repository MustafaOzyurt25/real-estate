package com.realestate.repository;

import com.realestate.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface FavoritesRepository extends JpaRepository<Favorite,Long> {

    @Transactional
    @Modifying
    void deleteByUserId(Long userId);
}
