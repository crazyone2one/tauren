package cn.master.tauren.job;

import cn.master.tauren.service.SinkInfoService;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Created by 11's papa on 12/20/2024
 **/
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class SinkInfoJob extends QuartzJobBean {
    private final SinkInfoService sinkInfoService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        sinkInfoService.generateSinkBaseInfo();
    }
}
