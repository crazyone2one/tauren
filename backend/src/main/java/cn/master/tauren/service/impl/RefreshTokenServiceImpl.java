package cn.master.tauren.service.impl;

import cn.master.tauren.entity.RefreshToken;
import cn.master.tauren.entity.User;
import cn.master.tauren.mapper.RefreshTokenMapper;
import cn.master.tauren.service.RefreshTokenService;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

/**
 * 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-02
 */
@Service
public class RefreshTokenServiceImpl extends ServiceImpl<RefreshTokenMapper, RefreshToken> implements RefreshTokenService {
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Override
    public RefreshToken createRefreshToken(String userId) {
        User user = QueryChain.of(User.class).eq(User::getId, userId).oneOpt()
                .orElseThrow(() -> new RuntimeException("User not found"));
        RefreshToken refreshToken = RefreshToken.builder()
                .revoked(false)
                .userId(user.getId())
                .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                .expiryDate(Instant.now().plusMillis(refreshExpiration).toEpochMilli())
                .deleted(false)
                .build();
        mapper.insert(refreshToken);
        return refreshToken;
    }
}
