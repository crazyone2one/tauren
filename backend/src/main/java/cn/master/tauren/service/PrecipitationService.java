package cn.master.tauren.service;

import com.mybatisflex.core.service.IService;
import cn.master.tauren.entity.Precipitation;

/**
 * 降水量基础数据表 服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-11
 */
public interface PrecipitationService extends IService<Precipitation> {
    /**
     * 降水量测点基础信息
     */
    void genPrecipitationCddyFile();

    /**
     * 正常降水量实时数据
     */
    void genPrecipitationCdssFile();

    /**
     * 降水量报警实时数据
     */
    void genPrecipitationAlarmFile(String alarmFlag);
}
