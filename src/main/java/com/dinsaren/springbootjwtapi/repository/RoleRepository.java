package com.dinsaren.springbootjwtapi.repository;

import com.dinsaren.springbootjwtapi.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
