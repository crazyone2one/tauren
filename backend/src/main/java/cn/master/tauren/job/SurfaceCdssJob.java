package cn.master.tauren.job;

import cn.master.tauren.service.SurfaceBaseInfoService;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 地表水实时数据 cron = "0 0/10 * * * ?"
 *
 * @author Created by 11's papa on 12/15/2024
 **/
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class SurfaceCdssJob extends QuartzJobBean {
    private final SurfaceBaseInfoService surfaceBaseInfoService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        surfaceBaseInfoService.generateSurfaceCdss();
    }
}
