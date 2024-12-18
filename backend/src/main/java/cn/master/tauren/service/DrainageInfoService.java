package cn.master.tauren.service;

import com.mybatisflex.core.service.IService;
import cn.master.tauren.entity.DrainageInfo;

/**
 * 排水量基础数据 服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-12
 */
public interface DrainageInfoService extends IService<DrainageInfo> {
    /**
     * 排水量测点基本信息
     */
    void drainageCddy();

    /**
     * 排水量实时数据
     */
    void drainageCdss();

    /**
     * 排水量异常数据
     */
    void drainageAlarm();

    /**
     * 水泵开停实时数据
     */
    void drainageStartStop();
}
