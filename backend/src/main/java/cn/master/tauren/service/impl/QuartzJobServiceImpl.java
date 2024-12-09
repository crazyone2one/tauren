package cn.master.tauren.service.impl;

import cn.master.tauren.constants.ScheduleConstants;
import cn.master.tauren.entity.QuartzJob;
import cn.master.tauren.mapper.QuartzJobMapper;
import cn.master.tauren.payload.request.BasePageRequest;
import cn.master.tauren.service.QuartzJobService;
import cn.master.tauren.util.ScheduleUtils;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 定时任务调度表 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-05
 */
@Service
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements QuartzJobService {
    private final QuartzJobMapper quartzJobMapper;
    private final SchedulerFactoryBean quartzSchedulerFactoryBean;

    public QuartzJobServiceImpl(QuartzJobMapper quartzJobMapper, SchedulerFactoryBean schedulerFactoryBean) {
        this.quartzJobMapper = quartzJobMapper;
        this.quartzSchedulerFactoryBean = schedulerFactoryBean;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertJob(QuartzJob job) {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = mapper.insert(job);
        if (rows > 0) {
            Scheduler scheduler = quartzSchedulerFactoryBean.getScheduler();
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateJob(QuartzJob job) throws SchedulerException {
        QuartzJob quartzJob = quartzJobMapper.selectOneById(job.getId());
        int rows = quartzJobMapper.update(job);
        if (rows > 0) {
            updateSchedulerJob(job, quartzJob.getJobGroup());
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changeStatus(QuartzJob job) throws SchedulerException {
        int rows = 0;
        String status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            rows = resumeJob(job);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            rows = pauseJob(job);
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resumeJob(QuartzJob job) throws SchedulerException {
        String jobId = job.getId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = quartzJobMapper.update(job);
        if (rows > 0) {
            Scheduler scheduler = quartzSchedulerFactoryBean.getScheduler();
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int pauseJob(QuartzJob job) throws SchedulerException {
        String jobId = job.getId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = quartzJobMapper.update(job);
        if (rows > 0) {
            Scheduler scheduler = quartzSchedulerFactoryBean.getScheduler();
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(QuartzJob job) throws SchedulerException {
        String jobId = job.getId();
        String jobGroup = job.getJobGroup();
        QuartzJob quartzJob = quartzJobMapper.selectOneById(jobId);
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, quartzJob);
        Scheduler scheduler = quartzSchedulerFactoryBean.getScheduler();
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
    }

    @Override
    public Page<QuartzJob> getAllJobs(BasePageRequest request) {
        return queryChain().page(Page.of(request.getPage(), request.getPageSize()));
    }

    private void updateSchedulerJob(QuartzJob job, String jobGroup) throws SchedulerException {
        String jobId = job.getId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        Scheduler scheduler = quartzSchedulerFactoryBean.getScheduler();
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }
}
