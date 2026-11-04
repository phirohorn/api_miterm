package com.dinsaren.springbootjwtapi.repository;

import com.dinsaren.springbootjwtapi.models.RefreshToken;
import com.dinsaren.springbootjwtapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    void deleteByUser(User user);
}
