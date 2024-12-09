package cn.master.tauren.job;

import cn.master.tauren.service.PersonnelRealTimeBehavior;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
@Slf4j
@RequiredArgsConstructor
@DisallowConcurrentExecution
public class SampleJob extends QuartzJobBean {
    private final PersonnelRealTimeBehavior personnelRealTimeBehavior;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        String now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        log.info("当前的时间: {}", now);
        log.info("当前任务[{}]执行的时间: {}", context.getScheduler(), context.getFireTime());
    }
}
