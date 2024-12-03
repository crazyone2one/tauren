package cn.master.tauren.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_employee_info")
public class EmployeeInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 人员识别卡编码
     */
    private String personCode;

    /**
     * 人员名称
     */
    private String personName;

    /**
     * 工种或职务
     */
    private String personWork;

    /**
     * 队组班组、部门
     */
    private String personPost;

    /**
     * 是否领导
     */
    private Boolean whetherLeader;

}
