package com.realestate.repository;

import com.realestate.entity.Role;
import com.realestate.entity.TourRequest;
import com.realestate.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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


    @Query("SELECT t FROM TourRequest t WHERE t.guestUser =:user")
    Page<TourRequest> getAuthCustomerTourRequestsPageable(/*String q,*/ User user, Pageable pageable);


    Optional<Object> findByResetPasswordCode(String resetToken);


    @Query(value = "SELECT role_name FROM roles WHERE id IN(SELECT role_id FROM user_roles WHERE user_roles.user_id = :id)" , nativeQuery = true)
    Set<String> getRolesById(Long id);
}
