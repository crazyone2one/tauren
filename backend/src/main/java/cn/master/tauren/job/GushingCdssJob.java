package cn.master.tauren.job;

import cn.master.tauren.service.GushingInfoService;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 涌水量实时数据 cron = "0 0/10 * * * ?
 *
 * @author Created by 11's papa on 12/15/2024
 **/
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class GushingCdssJob extends QuartzJobBean {
    private final GushingInfoService gushingInfoService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        gushingInfoService.generateGushingCdss();
    }
}
