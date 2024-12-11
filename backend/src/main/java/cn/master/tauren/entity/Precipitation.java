package cn.master.tauren.entity;

import com.mybatisflex.annotation.Column;
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
 * 降水量基础数据表 实体类。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_precipitation")
public class Precipitation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 测点编码
     */
    @Id
    private String id;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 设备安装位置
     */
    private String installLocation;

    /**
     * 安装日期
     */
    private LocalDateTime installDate;

    /**
     * 生产厂家
     */
    private String factory;

    /**
     * 检验日期
     */
    private LocalDateTime inspectionDate;

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
     * 删除标识
     */
    private Boolean deleted;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 更新时间
     */
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    /**
     * 测点编码
     */
    private String sensorId;

}
