package com.dinsaren.springbootjwtapi.security.jwt;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Access tokens revoked by logout. An entry is only needed until the token
 * would have expired anyway, so expired entries are dropped on each check.
 * Kept in memory: a server restart forgets it, which is acceptable because
 * access tokens are short-lived and the refresh token is deleted from the DB.
 */
@Component
public class TokenBlacklist {

    private final Map<String, Long> revoked = new ConcurrentHashMap<>();

    public void revoke(String token, long expiresAtMs) {
        revoked.put(token, expiresAtMs);
    }

    public boolean isRevoked(String token) {
        long now = System.currentTimeMillis();
        revoked.values().removeIf(expiresAt -> expiresAt < now);
        return revoked.containsKey(token);
    }
}
