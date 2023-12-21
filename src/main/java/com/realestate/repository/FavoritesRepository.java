package com.realestate.repository;

import com.realestate.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<Favorite,Long> {
}
