package cn.master.tauren.service;

import com.mybatisflex.core.service.IService;
import cn.master.tauren.entity.RefreshToken;

/**
 *  服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-02
 */
public interface RefreshTokenService extends IService<RefreshToken> {
    RefreshToken createRefreshToken(String userId);
}
