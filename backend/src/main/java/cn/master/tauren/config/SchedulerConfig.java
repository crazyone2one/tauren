package cn.master.tauren.config;

import cn.master.tauren.job.SampleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;

/**
 * @author Created by 11's papa on 12/03/2024
 **/
//@Configuration
public class SchedulerConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(SampleJob.class)
                .withIdentity("sampleJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger() {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();
    }
}
