package cn.master.tauren.controller;

import cn.master.tauren.entity.JobBean;
import cn.master.tauren.job.SampleJob;
import cn.master.tauren.service.PersonnelRealTimeBehavior;
import cn.master.tauren.util.JobUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
@RestController
@RequestMapping("/quartz")
@RequiredArgsConstructor
public class QuartzController {
    private final PersonnelRealTimeBehavior personnelRealTimeBehavior;
    private final Scheduler scheduler;

    private final String jobName = "sampleJob";

    @GetMapping("/create")
    public String createJob() {
        JobBean jobBean = new JobBean(jobName, SampleJob.class.getName(), "0/2 * * * * ?");
        JobUtils.createJob(scheduler, jobBean);
        return "create success";
    }

    @GetMapping("/pause")
    public String pauseJob() {
        JobUtils.pauseJob(scheduler, jobName);
        return "pause success";
    }

    @GetMapping("/resume")
    public String resumeJob() {
        JobUtils.resumeJob(scheduler, jobName);
        return "resume success";
    }

    @GetMapping("/delete")
    public String deleteJob() {
        JobUtils.deleteJob(scheduler, jobName);
        return "delete success";
    }

    @GetMapping("/once")
    public String runOnceJob() {
        JobUtils.runJobOnce(scheduler, jobName);
        return "once success";
    }

    @GetMapping("/modify")
    public String modifyJob() {
        JobBean jobBean = new JobBean(jobName, SampleJob.class.getName(), "0/5 * * * * ?");
        JobUtils.modifyJob(scheduler, jobBean);
        return "modify success";
    }

    @PostMapping("/gen")
    public void gen() {
        personnelRealTimeBehavior.genPersonnelFile();
    }

}
