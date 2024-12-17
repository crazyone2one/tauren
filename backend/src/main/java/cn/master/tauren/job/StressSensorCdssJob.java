package cn.master.tauren.job;

import cn.master.tauren.service.StressSensorService;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 钻孔应力基本信息
 * @author Created by 11's papa on 12/17/2024
 **/
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class StressSensorCdssJob extends QuartzJobBean {
    private final StressSensorService stressSensorService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        stressSensorService.stressSensorCdss();
    }
}
