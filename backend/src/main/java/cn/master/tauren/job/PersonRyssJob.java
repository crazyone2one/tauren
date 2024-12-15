package cn.master.tauren.job;

import cn.master.tauren.service.PersonnelRealTimeBehavior;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 人员实时数据文件 cron = "0 0/5 * * * ?"
 * @author Created by 11's papa on 12/15/2024
 **/
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class PersonRyssJob extends QuartzJobBean {
    private final PersonnelRealTimeBehavior personnelRealTimeBehavior;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        personnelRealTimeBehavior.genPersonnelFile();
    }
}
