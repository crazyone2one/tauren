package cn.master.tauren.service;

import com.mybatisflex.core.service.IService;
import cn.master.tauren.entity.HoleInfo;

/**
 * 长观孔测点基本信息 服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-16
 */
public interface HoleInfoService extends IService<HoleInfo> {
    void generateHoleInfo();

    void generateHoleRealData();

    /**
     * 长观孔测点异常数据
     */
    void generateHoleAlarmData();
}
