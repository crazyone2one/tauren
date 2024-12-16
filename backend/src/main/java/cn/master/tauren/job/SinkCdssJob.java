package cn.master.tauren.job;

import cn.master.tauren.service.SinkInfoService;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 地表岩移实时数据 cron = "0 0/5 * * * ?"
 *
 * @author Created by 11's papa on 12/16/2024
 **/
@RequiredArgsConstructor
@DisallowConcurrentExecution
public class SinkCdssJob extends QuartzJobBean {
    private final SinkInfoService service;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        service.generateSinkRealData();
    }
}
