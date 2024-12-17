package cn.master.tauren.job;

import cn.master.tauren.service.DrainageInfoService;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Created by 11's papa on 12/17/2024
 **/
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class DrainageAlarmJob extends QuartzJobBean {
    private final DrainageInfoService drainageInfoService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        drainageInfoService.drainageAlarm();
    }
}
