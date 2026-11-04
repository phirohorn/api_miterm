package com.dinsaren.springbootjwtapi.security.services;

import com.dinsaren.springbootjwtapi.exception.TokenRefreshException;
import com.dinsaren.springbootjwtapi.models.RefreshToken;
import com.dinsaren.springbootjwtapi.models.User;
import com.dinsaren.springbootjwtapi.repository.RefreshTokenRepository;
import com.dinsaren.springbootjwtapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${app.jwt.refresh-expiration-ms}")
    private long refreshTokenDurationMs;

    @Transactional
    public RefreshToken createRefreshToken(Integer userId) {
        User user = userRepository.getReferenceById(userId);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public RefreshToken verify(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenRefreshException("Refresh token is invalid. Please log in again."));
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new TokenRefreshException("Refresh token has expired. Please log in again.");
        }
        return refreshToken;
    }

    @Transactional
    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    @Transactional
    public void deleteByUserId(Integer userId) {
        refreshTokenRepository.deleteByUser(userRepository.getReferenceById(userId));
    }
}
