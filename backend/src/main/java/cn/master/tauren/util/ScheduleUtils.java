package cn.master.tauren.util;

import cn.master.tauren.constants.ScheduleConstants;
import cn.master.tauren.entity.JobBean;
import cn.master.tauren.entity.QuartzJob;
import cn.master.tauren.exception.QuartzDisallowConcurrentExecution;
import cn.master.tauren.exception.QuartzJobExecution;
import cn.master.tauren.exception.TaskException;
import org.quartz.*;

/**
 * 定时任务工具类
 *
 * @author Created by 11's papa on 12/03/2024
 **/
public class ScheduleUtils {
    /**
     * 构建任务触发对象
     *
     * @param jobId    job id
     * @param jobGroup job group
     * @return org.quartz.TriggerKey
     */
    public static TriggerKey getTriggerKey(String jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 创建定时任务
     *
     * @param scheduler 调度器
     * @param job       任务bean
     */
    public static void createScheduleJob(Scheduler scheduler, QuartzJob job) {
        Class<? extends Job> jobClass = getQuartzJobClass(job);
        try {
            // 构建job信息
            String jobId = job.getId();
            String jobGroup = job.getJobGroup();
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(getJobKey(jobId, jobGroup))
                    //.withDescription(jobBean.getDescription())
                    //.usingJobData(jobBean.getDataMap())
                    //.storeDurably()
                    .build();
            // 表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);
            // 按新的cronExpression表达式构建一个新的trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(getTriggerKey(jobId, jobGroup))
                    .withSchedule(cronScheduleBuilder)
                    .build();
            // 放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);
            // 判断是否存在
            if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
                // 防止创建时存在数据问题 先移除，然后在执行创建操作
                scheduler.deleteJob(getJobKey(jobId, jobGroup));
            }
            scheduler.scheduleJob(jobDetail, trigger);
            // 暂停任务
            if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
                scheduler.pauseJob(getJobKey(jobId, jobGroup));
            }
        } catch (SchedulerException | TaskException e) {
            throw new RuntimeException(e);
        }


    }

    private static CronScheduleBuilder handleCronScheduleMisfirePolicy(QuartzJob job, CronScheduleBuilder csb)
            throws TaskException {
        return switch (job.getMisfirePolicy()) {
            case ScheduleConstants.MISFIRE_DEFAULT -> csb;
            case ScheduleConstants.MISFIRE_IGNORE_MISFIRES -> csb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED -> csb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstants.MISFIRE_DO_NOTHING -> csb.withMisfireHandlingInstructionDoNothing();
            default -> throw new TaskException("The task misfire policy '" + job.getMisfirePolicy()
                    + "' cannot be used in cron schedule tasks", TaskException.Code.CONFIG_ERROR);
        };
    }

    /**
     * 构建任务键对象
     *
     * @param jobId
     * @param jobGroup
     * @return org.quartz.JobKey
     */
    public static JobKey getJobKey(String jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    private static Class<? extends Job> getQuartzJobClass(QuartzJob job) {
        boolean isConcurrent = "0".equals(job.getConcurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /**
     * 暂停任务
     *
     * @param scheduler 调度器
     * @param jobName   任务名
     */
    public static void pauseJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler 调度器
     * @param jobName   任务名
     */
    public static void resumeJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除任务
     *
     * @param scheduler 调度器
     * @param jobName   任务名
     */
    public static void deleteJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行一次定时任务
     *
     * @param scheduler 调度器
     * @param jobName   任务名
     */
    public static void runJobOnce(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改定时任务
     *
     * @param scheduler 调度器
     * @param jobBean   任务bean
     */
    public static void modifyJob(Scheduler scheduler, JobBean jobBean) {
        // 获取任务触发器的标识
        TriggerKey triggerKey = TriggerKey.triggerKey(jobBean.getJobName() + "_trigger");
        // 通过标识获取旧的触发器对象
        try {
            CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 使用新的corn表达式构建新的触发器
            String newCron = jobBean.getCronExpression();
            CronTrigger newTrigger = oldTrigger.getTriggerBuilder()
                    .withSchedule(CronScheduleBuilder.cronSchedule(newCron))
                    //.withSchedule(CronScheduleBuilder.cronSchedule(newCron).withMisfireHandlingInstructionDoNothing())
                    .build();
            //触发器更新任务的触发器
            scheduler.rescheduleJob(triggerKey, newTrigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

    }
}
