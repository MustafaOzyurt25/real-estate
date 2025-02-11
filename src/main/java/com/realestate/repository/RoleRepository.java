package com.realestate.repository;

import com.realestate.entity.Role;
import com.realestate.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long>
{

    @Query("SELECT r FROM Role r WHERE r.roleName = ?1")
    Optional<Role> findByEnumRoleEquals(RoleType role);

    @Query("SELECT (count(r) > 0) FROM Role r WHERE r.roleName = ?1")
    boolean existsByEnumUserRole(RoleType role);



}
