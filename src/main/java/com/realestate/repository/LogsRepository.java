package com.realestate.repository;

import com.realestate.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface LogsRepository extends JpaRepository<Log,Long>
{

    @Transactional
    @Modifying
    void deleteByUserId(Long userId);
}
