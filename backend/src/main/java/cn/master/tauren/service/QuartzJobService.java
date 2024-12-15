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

    boolean updateJob(QuartzJob job) throws SchedulerException;

    boolean changeStatus(QuartzJob job);

    boolean resumeJob(QuartzJob job);

    boolean pauseJob(QuartzJob job);

    void run(QuartzJob job) throws SchedulerException;

    boolean deleteJob(QuartzJob job);

    Page<QuartzJob> getAllJobs(BasePageRequest request);
}
