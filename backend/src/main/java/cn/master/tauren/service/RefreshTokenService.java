package cn.master.tauren.service;

import cn.master.tauren.payload.request.RefreshTokenRequest;
import cn.master.tauren.payload.response.RefreshTokenResponse;
import com.mybatisflex.core.service.IService;
import cn.master.tauren.entity.RefreshToken;

import java.util.Optional;

/**
 *  服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-02
 */
public interface RefreshTokenService extends IService<RefreshToken> {
    RefreshToken createRefreshToken(String userId);

    RefreshToken verifyExpiration(RefreshToken token);

    Optional<RefreshToken> findByToken(String token);

    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);
}
