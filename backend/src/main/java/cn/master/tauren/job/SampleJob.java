package cn.master.tauren.job;

import cn.master.tauren.service.PersonnelRealTimeBehavior;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
@Slf4j
@RequiredArgsConstructor
public class SampleJob implements Job {
    private final PersonnelRealTimeBehavior personnelRealTimeBehavior;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("SampleJob start:{}", context.getFireTime());
        //personnelRealTimeBehavior.genPersonnelFile();
    }
}
