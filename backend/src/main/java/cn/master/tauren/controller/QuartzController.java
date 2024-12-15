package cn.master.tauren.controller;

import cn.master.tauren.constants.Constants;
import cn.master.tauren.entity.QuartzJob;
import cn.master.tauren.payload.request.BasePageRequest;
import cn.master.tauren.ret.ResultHolder;
import cn.master.tauren.service.QuartzJobService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
@RestController
@RequestMapping("/quartz")
@RequiredArgsConstructor
@Tag(name = "任务管理")
public class QuartzController {
    private final QuartzJobService quartzJobService;

    @PostMapping("/create")
    @Operation(description = "创建定时任务")
    public ResultHolder createJob(@RequestBody QuartzJob job) {
        if (!CronExpression.isValidExpression(job.getCronExpression())) {
            return ResultHolder.error("新增任务'" + job.getJobName() + "'失败，Cron表达式不正确", null);
        } else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI)) {
            return ResultHolder.error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi://'调用", null);
        } else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_LDAP)) {
            return ResultHolder.error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap://'调用", null);
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(),
                new String[]{Constants.HTTP, Constants.HTTPS})) {
            return ResultHolder.error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'http(s)//'调用", null);
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), Constants.JOB_ERROR_STR)) {
            return ResultHolder.error("新增任务'" + job.getJobName() + "'失败，目标字符串存在违规", null);
        }
        return ResultHolder.success(quartzJobService.insertJob(job), "任务创建成功");
    }

    @PostMapping("/pause")
    public String pauseJob(@RequestBody QuartzJob job) {
        quartzJobService.pauseJob(job);
        return "pause success";
    }

    @PostMapping("/resume")
    public String resumeJob(@RequestBody QuartzJob job) {
        quartzJobService.resumeJob(job);
        return "resume success";
    }

    @PostMapping("/deleteJob")
    public boolean deleteJob(@RequestBody QuartzJob job) {
        return quartzJobService.deleteJob(job);
    }

    @PutMapping("/once")
    @Operation(description = "立即执行一次")
    public String runOnceJob(@RequestBody QuartzJob job) throws SchedulerException {
        quartzJobService.run(job);
        return "once success";
    }

    @PutMapping("/changeStatus")
    public ResultHolder changeStatus(@RequestBody QuartzJob job) throws SchedulerException {
        QuartzJob newJob = quartzJobService.getOneByEntityId(job);
        newJob.setStatus(job.getStatus());
        return ResultHolder.success(quartzJobService.changeStatus(newJob));
    }

    @PutMapping("/modify")
    @Operation(description = "修改定时任务")
    public ResultHolder modifyJob(@RequestBody QuartzJob job) throws SchedulerException {
        if (!CronExpression.isValidExpression(job.getCronExpression())) {
            return ResultHolder.error("修改任务'" + job.getJobName() + "'失败，Cron表达式不正确", null);
        } else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI)) {
            return ResultHolder.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi://'调用", null);
        } else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_LDAP)) {
            return ResultHolder.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap://'调用", null);
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(),
                new String[]{Constants.HTTP, Constants.HTTPS})) {
            return ResultHolder.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'http(s)//'调用", null);
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), Constants.JOB_ERROR_STR)) {
            return ResultHolder.error("修改任务'" + job.getJobName() + "'失败，目标字符串存在违规", null);
        }
        return ResultHolder.success(quartzJobService.updateJob(job));
    }

    @PostMapping("/page")
    @Operation(summary = "系统设置-系统-用户-分页查找用户")
    public Page<QuartzJob> page(@Validated @RequestBody BasePageRequest request) {
        return quartzJobService.getAllJobs(request);
    }
}
