package cn.master.tauren.job;

import cn.master.tauren.service.PumpInfoService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 疏（放）水实时数据
 *
 * @author Created by 11's papa on 12/12/2024
 **/
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class PumpCdssTask extends QuartzJobBean {

    private final PumpInfoService pumpInfoService;


    @Override
    protected void executeInternal(JobExecutionContext context) {
        pumpInfoService.generatePumpCdss();
    }
}
