package cn.master.tauren.job;

import cn.master.tauren.constants.ScheduleConstants;
import cn.master.tauren.entity.QuartzJob;
import cn.master.tauren.entity.QuartzJobLog;
import cn.master.tauren.mapper.QuartzJobLogMapper;
import cn.master.tauren.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Objects;

/**
 * @author Created by 11's papa on 12/05/2024
 **/
@Slf4j
public abstract class AbstractQuartzJob implements Job {
    /**
     * 线程本地变量
     */
    private static final ThreadLocal<Date> THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) {
        QuartzJob quartzJob = new QuartzJob();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES), quartzJob);
        try {
            before(context, quartzJob);
            doExecute(context, quartzJob);
            after(context, quartzJob, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, quartzJob, e);
        }
    }

    protected abstract void doExecute(JobExecutionContext context, QuartzJob quartzJob) throws Exception;

    protected void after(JobExecutionContext context, QuartzJob quartzJob, Exception e) {
        Date startTime = THREAD_LOCAL.get();
        THREAD_LOCAL.remove();
        final QuartzJobLog quartzJobLog = new QuartzJobLog();
        quartzJobLog.setJobLogId(quartzJob.getId());
        quartzJobLog.setJobName(quartzJob.getJobName());
        quartzJobLog.setJobGroup(quartzJob.getJobGroup());
        quartzJobLog.setInvokeTarget(quartzJob.getInvokeTarget());
        quartzJobLog.setStartTime(startTime);
        quartzJobLog.setStopTime(new Date());
        long runMs = quartzJobLog.getStopTime().getTime() - quartzJobLog.getStartTime().getTime();
        quartzJobLog.setJobMessage(quartzJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (Objects.nonNull(e)) {
            quartzJobLog.setStatus("1");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String str = sw.toString();
            String errorMsg = StringUtils.substring(str, 0, 2000);
            quartzJobLog.setExceptionInfo(errorMsg);
        } else {
            quartzJobLog.setStatus("0");
        }
        QuartzJobLogMapper quartzJobLogMapper = SpringUtils.getBean(QuartzJobLogMapper.class);
        quartzJobLogMapper.insert(quartzJobLog);
    }

    protected void before(JobExecutionContext context, QuartzJob quartzJob) {
        THREAD_LOCAL.set(new Date());
    }
}
