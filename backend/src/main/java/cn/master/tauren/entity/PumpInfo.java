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
 * 疏放水钻孔基础数据 实体类。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_pump_info")
public class PumpInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 疏放水位置
     */
    private String installLocation;

    /**
     * 测点单位
     */
    private String measurementPointUnit;

    /**
     * 疏放目标层位
     */
    private String layerTarget;

    /**
     * 层厚度
     */
    private String layerThickness;

    /**
     * 层埋深
     */
    private String layerDepth;

    /**
     * 孔深
     */
    private String holeDepth;

    /**
     * 孔径
     */
    private String holeDiam;

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
     * 单孔流量
     */
    private String holeFlow;

    /**
     * 总疏放水量
     */
    private String totalFlow;

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
