package cn.master.tauren.job;

import cn.master.tauren.service.*;
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
    private final PrecipitationService precipitationService;
    private final SurfaceBaseInfoService surfaceBaseInfoService;
    private final GushingInfoService gushingInfoService;
    private final PumpInfoService pumpInfoService;

    //@Scheduled(cron = "0 0/1 * * * ?") // 每1分钟执行一次
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
     * 降水量基础数据,每天生成一次文件
     */
    @Scheduled(cron = "0 0 5 * * ?")
    public void jslCddyFile() {
        precipitationService.genPrecipitationCddyFile();
    }

    /**
     * 降水量实时数据,每10分钟生成一次文件
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void jslCdssFile() {
        precipitationService.genPrecipitationCdssFile();
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

    /**
     * 地表水基础数据
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void surfaceCddyFile() {
        surfaceBaseInfoService.generateSurfaceBaseInfo();
    }

    /**
     * 地表水实时数据
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void surfaceCdssFile() {
        surfaceBaseInfoService.generateSurfaceCdss();
    }

    /**
     * 涌水量基础数据
     */
    @Scheduled(cron = "0 0 14 * * ?")
    public void gushingCddyFile() {
        gushingInfoService.generateGushingInfo();
    }

    /**
     * 涌水量实时数据
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void gushingCdssFile() {
        gushingInfoService.generateGushingCdss();
    }

    /**
     * 疏（放）水监测测点基础信息
     */
    @Scheduled(cron = "0 0 14 * * ?")
    public void pumpCddyFile() {
        pumpInfoService.generatePumpInfo();
    }

    /**
     * 疏（放）水实时数据
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void pumpCdssFile() {
        pumpInfoService.generatePumpCdss();
    }


    public void withParams(String param) {
        log.info("执行方法[withParams],获取到的参数为{}", param);
    }
}
