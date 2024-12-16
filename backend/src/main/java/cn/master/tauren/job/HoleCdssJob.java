package cn.master.tauren.job;

import cn.master.tauren.service.HoleInfoService;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 长观孔实时数据 cron = "0 0/10 * * * ?"
 * @author Created by 11's papa on 12/16/2024
 **/
@RequiredArgsConstructor
@DisallowConcurrentExecution
public class HoleCdssJob extends QuartzJobBean {
    private final HoleInfoService holeInfoService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        holeInfoService.generateHoleRealData();
    }
}
