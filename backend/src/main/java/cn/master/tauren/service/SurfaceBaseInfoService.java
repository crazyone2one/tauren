package cn.master.tauren.service;

import com.mybatisflex.core.service.IService;
import cn.master.tauren.entity.SurfaceBaseInfo;

/**
 * 地表水基础数据 服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-11
 */
public interface SurfaceBaseInfoService extends IService<SurfaceBaseInfo> {
    void generateSurfaceBaseInfo();

    void generateSurfaceCdss();
}