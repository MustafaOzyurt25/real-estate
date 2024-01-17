package com.realestate.repository;

import com.realestate.entity.LogUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogUserRepository extends JpaRepository<LogUser, Long> {
}
