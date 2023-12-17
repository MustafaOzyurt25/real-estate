package com.realestate.repository;

import com.realestate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    User findByEmailEquals(String email);

    Optional<User> findByEmail(String userEmail);
}
