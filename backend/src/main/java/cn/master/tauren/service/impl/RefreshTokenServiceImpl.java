package cn.master.tauren.service.impl;

import cn.master.tauren.security.JwtTokenProvider;
import cn.master.tauren.constants.ResultCode;
import cn.master.tauren.entity.CustomUser;
import cn.master.tauren.entity.RefreshToken;
import cn.master.tauren.entity.User;
import cn.master.tauren.exception.TokenException;
import cn.master.tauren.mapper.RefreshTokenMapper;
import cn.master.tauren.payload.request.RefreshTokenRequest;
import cn.master.tauren.payload.response.RefreshTokenResponse;
import cn.master.tauren.service.RefreshTokenService;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl extends ServiceImpl<RefreshTokenMapper, RefreshToken> implements RefreshTokenService {
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Override
    public RefreshToken createRefreshToken(String userId) {
        User user = QueryChain.of(User.class).eq(User::getId, userId).oneOpt()
                .orElseThrow(() -> new RuntimeException("User not found"));
        RefreshToken refreshToken = RefreshToken.builder()
                .revoked(false)
                .userId(user.getId())
                .token(jwtTokenProvider.generateRefreshToken(user.getUsername()))
                .expiryDate(LocalDateTime.now().plusSeconds(refreshExpiration))
                .deleted(false)
                .build();
        mapper.insert(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (Objects.isNull(token)) {
            log.error("token is null");
            throw new TokenException(ResultCode.TOKEN_ERROR_02, ResultCode.TOKEN_ERROR_02.getMessage(), null);
        }
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            mapper.delete(token);
            throw new TokenException(ResultCode.TOKEN_ERROR_04, "Refresh token was expired. Please make a new authentication request", null);
        }
        return token;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return queryChain().eq(RefreshToken::getToken, token).oneOpt();
    }

    @Override
    public RefreshTokenResponse generateNewToken(RefreshTokenRequest request) {
        String userId = findByToken(request.getRefreshToken())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUserId)
                .orElseThrow(() -> new TokenException(ResultCode.TOKEN_ERROR_03, ResultCode.TOKEN_ERROR_03.getMessage(), null));
        CustomUser user = QueryChain.of(User.class).eq(User::getId, userId).oneAs(CustomUser.class);
        String token = jwtTokenProvider.generateAccessToken(user.getUsername());
        return RefreshTokenResponse.builder().accessToken(token)
                .refreshToken(request.getRefreshToken())
                .build();
    }
}
