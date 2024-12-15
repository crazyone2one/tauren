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
 * 涌水量基础数据 实体类。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_gushing_info")
public class GushingInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 传感器安装位置
     */
    private String installLocation;

    /**
     * 覆盖范围
     */
    private String coverage;

    /**
     * 传感器类型
     */
    private String sensorType;

    /**
     * 测点单位
     */
    private String measurementPointUnit;

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