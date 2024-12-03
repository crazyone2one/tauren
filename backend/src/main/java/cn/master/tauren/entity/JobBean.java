package cn.master.tauren.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by 11's papa on 12/03/2024
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobBean {
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务类
     */
    private String jobClass;
    /**
     * corn表达式
     */
    private String cronExpression;
}
