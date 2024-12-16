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
 * 长观孔测点基本信息 实体类。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_hole_info")
public class HoleInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 传感器类型
     */
    private String type;

    /**
     * 传感器安装位置
     */
    private String position;

    /**
     * 观测地层
     */
    private String layerStrata;

    /**
     * 观测层厚
     */
    private String layerThickness;

    /**
     * 观测层深
     */
    private String layerDepth;

    /**
     * 孔深
     */
    private String holeDepth;

    /**
     * 孔径
     */
    private String aperture;

    /**
     * 孔口坐标X
     */
    private String x;

    /**
     * 孔口坐标y
     */
    private String y;

    /**
     * 孔口坐标z
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
