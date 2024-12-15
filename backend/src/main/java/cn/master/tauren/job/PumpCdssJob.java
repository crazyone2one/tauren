package cn.master.tauren.job;

import cn.master.tauren.service.PumpInfoService;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 疏（放）水实时数据 cron = "0 0/5 * * * ?"
 *
 * @author Created by 11's papa on 12/15/2024
 **/
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class PumpCdssJob extends QuartzJobBean {
    private final PumpInfoService pumpInfoService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        pumpInfoService.generatePumpCdss();
    }
}
