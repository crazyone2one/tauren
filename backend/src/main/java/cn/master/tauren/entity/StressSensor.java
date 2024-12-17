package cn.master.tauren.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 钻孔应力测点基本信息 实体类。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_stress_sensor")
public class StressSensor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 业务编码
     */
    @Id(keyType = KeyType.Generator,value = KeyGenerators.flexId)
    private String id;

    /**
     * 测点编码
     */
    private String sensorId;

    /**
     * 监测区名称
     */
    private String sensorAreaName;

    /**
     * 传感器类型
     */
    private String pointType;

    /**
     * 传感器位置
     */
    private String pointName;

    /**
     * 应力计安装深度
     */
    private String installDepth;

    /**
     * 安装时间
     */
    private LocalDateTime installTime;

    /**
     * 应力计坐标X
     */
    private String x;

    /**
     * 应力计坐标Y
     */
    private String y;

    /**
     * 应力计坐标Z
     */
    private String z;

    /**
     * 应力计方向
     */
    private String direction;

    /**
     * 初始应力
     */
    private String startValue;

    /**
     * 删除标识
     */
    private Boolean deleted;

}
