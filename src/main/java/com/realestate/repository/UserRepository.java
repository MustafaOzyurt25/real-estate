package com.realestate.repository;

import com.realestate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    User findByEmailEquals(String email);


    @Query(value = "SELECT (count(u) = 0) FROM User u")
    boolean isEmpty();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM User u WHERE u.builtIn = false")
    void deleteUsers();

    Optional<User> findByEmail(String userEmail);

}
