package com.dinsaren.springbootjwtapi.dto;

import java.util.List;

public record JwtRes(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn,
        UserRes user) {

    public record UserRes(Integer id, String username, String email, List<String> roles) {
    }
}
