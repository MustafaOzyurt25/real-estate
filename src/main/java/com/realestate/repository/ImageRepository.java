package com.realestate.repository;

import com.realestate.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long>
{
    @Query(value = "SELECT (count(i) = 0) FROM Image i")
    boolean isEmpty();


}
