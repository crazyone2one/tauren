package cn.master.tauren.job;

import cn.master.tauren.service.PersonnelRealTimeBehavior;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Created by 11's papa on 12/09/2024
 **/
@Slf4j
@Component("customTask")
@RequiredArgsConstructor
public class CustomTask {
    private final PersonnelRealTimeBehavior personnelRealTimeBehavior;

    @Scheduled(cron = "0 0/1 * * * ?") // 每1分钟执行一次
    public void noParams() {
        log.info("执行无参方法");
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void ryssFile() {
        personnelRealTimeBehavior.genPersonnelFile();
    }

    /**
     * 长观孔测点数据,每天生成一次文件
     */
    @Scheduled(cron = "0 10 13 * * ?")
    public void cgkFile() {
        // todo 长观孔测点数据
        log.info("生成长观孔测点数据文件");
    }

    /**
     * 降水量数据,每10分钟生成一次文件
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void jslFile() {
        // todo 降水量数据
        log.info("生成降水量数据文件");
    }

    /**
     * 排水量数据,每10分钟生成一次文件
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void pslFile() {
        // todo 排水量数据
        log.info("生成排水量数据文件");
    }

    /**
     * 涌水量数据,每10分钟生成一次文件
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void yslFile() {
        // todo 涌水量数据
        log.info("生成涌水量数据文件");
    }

    public void dbsFile() {
        // todo 地表水数据
        log.info("生成地表水数据文件");
    }

    public void withParams(String param) {
        log.info("执行方法[withParams],获取到的参数为{}", param);
    }
}
