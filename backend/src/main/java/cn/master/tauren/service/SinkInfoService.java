package cn.master.tauren.service;

import com.mybatisflex.core.service.IService;
import cn.master.tauren.entity.SinkInfo;

/**
 * 地表岩移基础数据 服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-16
 */
public interface SinkInfoService extends IService<SinkInfo> {
    void generateSinkRealData();
}
