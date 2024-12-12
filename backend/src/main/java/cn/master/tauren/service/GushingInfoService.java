package cn.master.tauren.service;

import com.mybatisflex.core.service.IService;
import cn.master.tauren.entity.GushingInfo;

/**
 * 涌水量基础数据 服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-11
 */
public interface GushingInfoService extends IService<GushingInfo> {
    void generateGushingInfo();

    void generateGushingCdss();
}
