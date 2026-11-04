package com.dinsaren.springbootjwtapi.services;

import com.dinsaren.springbootjwtapi.dto.JwtRes;
import com.dinsaren.springbootjwtapi.dto.LoginReq;
import com.dinsaren.springbootjwtapi.models.RefreshToken;
import com.dinsaren.springbootjwtapi.models.User;
import com.dinsaren.springbootjwtapi.security.jwt.JwtUtils;
import com.dinsaren.springbootjwtapi.security.jwt.TokenBlacklist;
import com.dinsaren.springbootjwtapi.security.services.RefreshTokenService;
import com.dinsaren.springbootjwtapi.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String TOKEN_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final TokenBlacklist tokenBlacklist;
    private final RefreshTokenService refreshTokenService;

    public JwtRes login(LoginReq req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = jwtUtils.generateTokenFromUsername(user.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        return new JwtRes(accessToken, refreshToken.getToken(), TOKEN_TYPE, jwtUtils.getExpirationMs(),
                new JwtRes.UserRes(user.getId(), user.getUsername(), user.getEmail(), user.getRoleNames()));
    }

    /** Issues a new access token and rotates the refresh token. */
    @Transactional
    public JwtRes refresh(String refreshTokenValue) {
        RefreshToken oldToken = refreshTokenService.verify(refreshTokenValue);
        User user = oldToken.getUser();
        UserDetailsImpl details = new UserDetailsImpl(user);

        String accessToken = jwtUtils.generateTokenFromUsername(user.getUsername());
        RefreshToken newToken = refreshTokenService.createRefreshToken(user.getId());
        refreshTokenService.delete(oldToken);
        return new JwtRes(accessToken, newToken.getToken(), TOKEN_TYPE, jwtUtils.getExpirationMs(),
                new JwtRes.UserRes(user.getId(), user.getUsername(), user.getEmail(), details.getRoleNames()));
    }

    /** Revokes the current access token and every refresh token of the user. */
    public void logout(UserDetailsImpl user, String accessToken) {
        if (accessToken != null) {
            tokenBlacklist.revoke(accessToken, jwtUtils.getExpirationFromJwtToken(accessToken).getTime());
        }
        refreshTokenService.deleteByUserId(user.getId());
    }
}
