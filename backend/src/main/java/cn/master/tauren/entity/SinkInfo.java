package cn.master.tauren.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地表岩移基础数据 实体类。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_sink_info")
public class SinkInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 开采深度
     */
    private String miningDepth;

    /**
     * 区域面积
     */
    private String areaExtent;

    /**
     * 采区边界
     */
    private String areaBoundary;

    /**
     * 开采方式
     */
    private String exploitationMethod;

    /**
     * 桩点编号
     */
    private String pointId;

    /**
     * 坐标x
     */
    private String x;

    /**
     * 坐标y
     */
    private String y;

    /**
     * 坐标z
     */
    private String z;

    /**
     * 数据定义时间
     */
    private LocalDateTime dataTime;

    /**
     * 测点编码
     */
    private String sensorId;

    /**
     * 删除标识
     */
    private Boolean deleted;

}
