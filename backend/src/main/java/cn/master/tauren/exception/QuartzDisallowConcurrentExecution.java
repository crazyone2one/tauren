package cn.master.tauren.exception;

import cn.master.tauren.entity.QuartzJob;
import cn.master.tauren.job.AbstractQuartzJob;
import cn.master.tauren.util.JobInvokeUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * @author Created by 11's papa on 12/05/2024
 **/
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, QuartzJob quartzJob) throws Exception {
        JobInvokeUtils.invokeMethod(quartzJob);
    }
}
