package com.dinsaren.springbootjwtapi.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshReq(@NotBlank(message = "Refresh token is required") String refreshToken) {
}
