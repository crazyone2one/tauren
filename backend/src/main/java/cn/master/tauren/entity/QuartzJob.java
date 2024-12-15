package cn.master.tauren.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定时任务调度表 实体类。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_qrtz_job")
public class QuartzJob implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    private String id;

    /**
     * 任务名称
     */
    @NotBlank
    @Schema(description = "任务名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "abc")
    private String jobName;

    /**
     * 任务组名
     */
    @NotBlank
    @Schema(description = "任务组名", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "abc")
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @NotBlank
    @Schema(description = "任务名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "customTask.noParams()")
    private String invokeTarget;
    private String jobClass;
    private String interfaceName;
    /**
     * cron执行表达式
     */
    @NotBlank
    @Schema(description = "cron执行表达式", requiredMode = Schema.RequiredMode.REQUIRED, example = "0 0/5 * * * ?")
    private String cronExpression;

    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行
     */
    @NotBlank
    @Schema(description = "计划执行错误策略", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private String misfirePolicy;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    private String concurrent;

    /**
     * 状态（0正常 1暂停）
     */
    private String status;


    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    /**
     * 备注信息
     */
    private String remark;

    private boolean deleted;
    private Boolean cronJob;

    @Column(ignore = true)
    private Long repeatTime;
}
