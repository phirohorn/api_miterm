package com.dinsaren.springbootjwtapi.controllers;

import com.dinsaren.springbootjwtapi.dto.JwtRes;
import com.dinsaren.springbootjwtapi.dto.LoginReq;
import com.dinsaren.springbootjwtapi.dto.MessageRes;
import com.dinsaren.springbootjwtapi.dto.TokenRefreshReq;
import com.dinsaren.springbootjwtapi.security.jwt.AuthTokenFilter;
import com.dinsaren.springbootjwtapi.security.services.UserDetailsImpl;
import com.dinsaren.springbootjwtapi.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Login, token refresh and logout")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Log in and receive an access token and a refresh token")
    @PostMapping("/login")
    public JwtRes login(@Valid @RequestBody LoginReq req) {
        return authService.login(req);
    }

    @Operation(summary = "Exchange a refresh token for a new access token")
    @PostMapping("/refresh")
    public JwtRes refresh(@Valid @RequestBody TokenRefreshReq req) {
        return authService.refresh(req.refreshToken());
    }

    @Operation(summary = "Get the logged-in user")
    @GetMapping("/me")
    public JwtRes.UserRes me(@AuthenticationPrincipal UserDetailsImpl user) {
        return new JwtRes.UserRes(user.getId(), user.getUsername(), user.getEmail(), user.getRoleNames());
    }

    @Operation(summary = "Log out: revokes the current access token and the refresh tokens")
    @PostMapping("/logout")
    public MessageRes logout(@AuthenticationPrincipal UserDetailsImpl user, HttpServletRequest request) {
        authService.logout(user, AuthTokenFilter.parseJwt(request));
        return new MessageRes("Logged out successfully");
    }
}
