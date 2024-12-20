package cn.master.tauren.job;

import cn.master.tauren.service.HoleInfoService;
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
public class HoleInfoJob extends QuartzJobBean {
    private final HoleInfoService holeInfoService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        holeInfoService.generateHoleInfo();
    }
}
