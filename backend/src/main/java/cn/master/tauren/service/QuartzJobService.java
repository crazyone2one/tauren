package cn.master.tauren.service;

import cn.master.tauren.payload.request.BasePageRequest;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import cn.master.tauren.entity.QuartzJob;
import org.quartz.SchedulerException;

/**
 * 定时任务调度表 服务层。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-05
 */
public interface QuartzJobService extends IService<QuartzJob> {
    int insertJob(QuartzJob job);

    int updateJob(QuartzJob job) throws SchedulerException;

    int changeStatus(QuartzJob job) throws SchedulerException;

    int resumeJob(QuartzJob job) throws SchedulerException;

    int pauseJob(QuartzJob job) throws SchedulerException;

    void run(QuartzJob job) throws SchedulerException;

    Page<QuartzJob> getAllJobs(BasePageRequest request);
}
