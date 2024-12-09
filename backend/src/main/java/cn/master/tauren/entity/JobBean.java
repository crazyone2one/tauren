package cn.master.tauren.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobKey;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Created by 11's papa on 12/03/2024
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class JobBean implements Job, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String JOB_MAP_KEY = "self";

    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "0";
    public static final String CONCURRENT_IS = "1";
    public static final String CONCURRENT_NOT = "0";
    /**
     * 任务名称
     */
    private String jobName;
    private String jobGroup;
    private String jobStatus;
    private String description;
    /**
     * 任务是否有状态
     */
    private String isConcurrent;
    private String springBean;
    private String methodName;
    /**
     * 任务类
     */
    private Class jobClass = this.getClass();
    /**
     * corn表达式
     */
    private String cronExpression;

    public JobKey getJobKey() {
        // 任务名称和组构成任务key
        return JobKey.jobKey(jobName, jobGroup);
    }

    /**
     * 为了将执行后的任务持久化到数据库中
     */
    @JsonIgnore
    private JobDataMap dataMap = new JobDataMap();

    public JobDataMap getDataMap() {
        if (dataMap.isEmpty()) {
            dataMap.put(JOB_MAP_KEY, this);
        }
        return dataMap;
    }
}
