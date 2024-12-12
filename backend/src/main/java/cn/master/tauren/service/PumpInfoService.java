package cn.master.tauren.service;

import com.mybatisflex.core.service.IService;
import cn.master.tauren.entity.PumpInfo;

/**
 * 疏放水钻孔基础数据 服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-12
 */
public interface PumpInfoService extends IService<PumpInfo> {
    /**
     * 疏（放）水监测测点基础信息
     */
    void generatePumpInfo();

    /**
     * 疏（放）水实时数据
     */
    void generatePumpCdss();
}
