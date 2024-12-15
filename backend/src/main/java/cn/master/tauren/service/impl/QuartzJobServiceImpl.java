package cn.master.tauren.service.impl;

import cn.master.tauren.constants.ScheduleConstants;
import cn.master.tauren.entity.QuartzJob;
import cn.master.tauren.mapper.QuartzJobMapper;
import cn.master.tauren.payload.request.BasePageRequest;
import cn.master.tauren.service.QuartzJobService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 定时任务调度表 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements QuartzJobService {
    private final QuartzJobMapper quartzJobMapper;
    private final SchedulerFactoryBean schedulerFactoryBean;
    private final Scheduler scheduler;
    private final ApplicationContext context;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertJob(QuartzJob job) {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        job.setCronJob(true);
        job.setJobClass(job.getInvokeTarget());
        scheduleNewJob(job);
        log.info(">>>>> jobName = [{}] created.", job.getJobName());
        return 1;
    }

    @SuppressWarnings("unchecked")
    private void scheduleNewJob(QuartzJob quartzJob) {
        try {
            //Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobDetail jobDetail = JobBuilder
                    .newJob((Class<? extends QuartzJobBean>) Class.forName(quartzJob.getJobClass()))
                    .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                    .usingJobData("jobData", quartzJob.getJobName())
                    .usingJobData("executeData", "test")
                    .build();
            if (!scheduler.checkExists(jobDetail.getKey())) {
                //jobDetail = scheduleCreator.createJob((Class<? extends QuartzJobBean>) Class.forName(quartzJob.getJobClass()), false, context, quartzJob.getJobName(), quartzJob.getJobGroup());
                jobDetail = JobBuilder
                        .newJob((Class<? extends QuartzJobBean>) Class.forName(quartzJob.getJobClass()))
                        .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                        .usingJobData("jobData", quartzJob.getJobName())
                        .usingJobData("executeData", "test")
                        .build();
                //Trigger trigger = scheduleCreator.createCronTrigger(quartzJob.getJobName(), new Date(), quartzJob.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                        .forJob(jobDetail)
                        .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()))
                        .build();
                //if (quartzJob.getCronJob()) {
                //    trigger = scheduleCreator.createCronTrigger(quartzJob.getJobName(), new Date(), quartzJob.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                //} else {
                //    trigger = scheduleCreator.createSimpleTrigger(quartzJob.getJobName(), new Date(), quartzJob.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                //}
                //boolean flag = scheduler.checkExists(trigger.getKey());
                //if (!flag) {
                //    scheduler.start();
                //    scheduler.scheduleJob(jobDetail, trigger);
                //}
                scheduler.scheduleJob(jobDetail, trigger);
                quartzJob.setStatus(ScheduleConstants.Status.NORMAL.getValue());
                quartzJob.setRemark("i am job number " + quartzJob.getJobName());
                quartzJob.setInterfaceName("interface_" + quartzJob.getJobName());
                mapper.insert(quartzJob);
                log.info(">>>>> jobName = [{}] scheduled.", quartzJob.getJobName());
            } else {
                log.error("scheduleNewJobRequest.jobAlreadyExist");
            }
        } catch (ClassNotFoundException e) {
            log.error("Class Not Found - {}", quartzJob.getJobClass(), e);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateJob(QuartzJob job) {
        try {
            JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
            if (!CronExpression.isValidExpression(job.getCronExpression()) || !scheduler.checkExists(jobKey)) {
                return false;
            }
            mapper.update(job);
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            Trigger build = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression())).build();
            scheduler.rescheduleJob(triggerKey, build);
            return true;
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changeStatus(QuartzJob job) {
        String status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            return resumeJob(job);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            return pauseJob(job);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resumeJob(QuartzJob job) {
        try {
            JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
            if (!scheduler.checkExists(jobKey)) {
                return false;
            }
            job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
            quartzJobMapper.update(job);
            scheduler.resumeJob(new JobKey(job.getJobName(), job.getJobGroup()));
            log.info(">>>>> jobName = [{}] resumed.", job.getJobName());
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to resume job - {}", job.getJobName(), e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean pauseJob(QuartzJob job) {
        try {
            JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
            if (!scheduler.checkExists(jobKey)) {
                return false;
            }
            job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
            quartzJobMapper.update(job);
            scheduler.pauseJob(jobKey);
            log.info(">>>>> jobName = [{}] paused.", job.getJobName());
            return true;
        } catch (SchedulerException e) {
            log.error("Failed to pause job - {}", job.getJobName(), e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(QuartzJob job) throws SchedulerException {
        //QuartzJob quartzJob = getById(job.getId());
        scheduler.triggerJob(new JobKey(job.getJobName(), job.getJobGroup()));
        log.info(">>>>> jobName = [{}] scheduled and started now.", job.getJobName());
    }

    @Override
    public boolean deleteJob(QuartzJob job) {
        try {
            QuartzJob getJobInfo = getById(job.getId());
            mapper.delete(getJobInfo);
            log.info(">>>>> jobName = [{}] deleted.", getJobInfo.getJobName());
            return scheduler.deleteJob(new JobKey(getJobInfo.getJobName(), getJobInfo.getJobGroup()));
        } catch (SchedulerException e) {
            log.error("Failed to delete job - {}", job.getJobName(), e);
            return false;
        }
    }

    @Override
    public Page<QuartzJob> getAllJobs(BasePageRequest request) {
        return queryChain().page(Page.of(request.getPage(), request.getPageSize()));
    }
}
