package cn.master.tauren.service.impl;

import cn.master.tauren.security.JwtTokenProvider;
import cn.master.tauren.entity.CustomUser;
import cn.master.tauren.entity.RefreshToken;
import cn.master.tauren.entity.User;
import cn.master.tauren.payload.request.AuthenticationRequest;
import cn.master.tauren.payload.response.AuthenticationResponse;
import cn.master.tauren.service.AuthenticationService;
import cn.master.tauren.service.RefreshTokenService;
import com.mybatisflex.core.query.QueryChain;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        CustomUser user = QueryChain.of(User.class)
                .eq(User::getUsername, request.getUsername()).oneAsOpt(CustomUser.class)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        String token = jwtTokenProvider.generateToken(user);
        return AuthenticationResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .accessToken(token)
                .refreshToken(refreshToken.getToken())
                .build();
    }
}
