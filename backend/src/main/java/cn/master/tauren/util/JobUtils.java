package cn.master.tauren.util;

import cn.master.tauren.entity.JobBean;
import org.quartz.*;

/**
 * @author Created by 11's papa on 12/03/2024
 **/
public class JobUtils {
    /**
     * 创建定时任务
     *
     * @param scheduler 调度器
     * @param jobBean   任务bean
     */
    public static void createJob(Scheduler scheduler, JobBean jobBean) {
        String corn = "0/2 * * * * ?";
        try {
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(jobBean.getJobClass());
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobBean.getJobName())
                    .storeDurably()
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(jobBean.getJobName() + "_trigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule(corn))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ClassNotFoundException | SchedulerException e) {
            throw new RuntimeException(e);
        }


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
