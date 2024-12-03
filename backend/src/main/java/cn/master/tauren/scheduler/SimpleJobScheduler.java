package cn.master.tauren.scheduler;

import cn.master.tauren.job.SampleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
@Configuration
public class SimpleJobScheduler {
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(SampleJob.class)
                .withIdentity("sampleJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger() {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(5)
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("trigger")
                .withSchedule(simpleScheduleBuilder)
                .build();
    }
}
