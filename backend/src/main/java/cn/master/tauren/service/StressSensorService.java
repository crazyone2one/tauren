package cn.master.tauren.service;

import com.mybatisflex.core.service.IService;
import cn.master.tauren.entity.StressSensor;

/**
 * 钻孔应力测点基本信息 服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-17
 */
public interface StressSensorService extends IService<StressSensor> {
    /**
     * 钻孔应力测点基本信息
     */
    void stressSensorInfo();

    /**
     * 钻孔应力测点实时数据
     */
    void stressSensorCdss();

    /**
     * 钻孔应力测点异常数据
     */
    void stressSensorAlarm();
}
