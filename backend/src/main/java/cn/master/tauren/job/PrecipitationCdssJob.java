package cn.master.tauren.job;

import cn.master.tauren.service.PrecipitationService;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 降水量实时数据,每10分钟生成一次文件 cron = "0 0/10 * * * ?"
 *
 * @author Created by 11's papa on 12/15/2024
 **/
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class PrecipitationCdssJob extends QuartzJobBean {
    private final PrecipitationService precipitationService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        precipitationService.genPrecipitationCdssFile();
    }
}
