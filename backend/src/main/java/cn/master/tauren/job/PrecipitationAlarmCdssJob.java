package cn.master.tauren.job;

import cn.master.tauren.service.PrecipitationService;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Created by 11's papa on 12/16/2024
 **/
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class PrecipitationAlarmCdssJob extends QuartzJobBean {
    private final PrecipitationService precipitationService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        precipitationService.genPrecipitationAlarmFile("1");
    }
}
