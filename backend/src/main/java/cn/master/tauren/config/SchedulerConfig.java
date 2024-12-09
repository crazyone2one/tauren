package cn.master.tauren.config;

import cn.master.tauren.job.SampleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by 11's papa on 12/03/2024
 **/
//@Configuration
public class SchedulerConfig {
    private final String job = "sampleJob";

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(SampleJob.class)
                .withIdentity(job)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger() {
        //CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
        // 错过的全部补偿，然后正常调度
        // CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?").withMisfireHandlingInstructionIgnoreMisfires();
        // 错过的全部合并成一次，并立即补偿，然后正常调度
        // CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?").withMisfireHandlingInstructionFireAndProceed();
        // 错过的不再补偿
         CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/5 * * * ?").withMisfireHandlingInstructionDoNothing();
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity(job + "_trigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
