package com.realestate.repository;

import com.realestate.entity.LogAdvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface LogsAdvertRepository extends JpaRepository<LogAdvert,Long>
{

    @Transactional
    @Modifying
    void deleteByUserId(Long userId);
}
