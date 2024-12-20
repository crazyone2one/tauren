package cn.master.tauren.service.impl;

import cn.master.tauren.entity.Precipitation;
import cn.master.tauren.util.DateUtils;
import cn.master.tauren.util.FileUtils;
import cn.master.tauren.util.StringUtils;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.master.tauren.entity.SurfaceBaseInfo;
import cn.master.tauren.mapper.SurfaceBaseInfoMapper;
import cn.master.tauren.service.SurfaceBaseInfoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 地表水基础数据 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-11
 */
@Service
public class SurfaceBaseInfoServiceImpl extends ServiceImpl<SurfaceBaseInfoMapper, SurfaceBaseInfo> implements SurfaceBaseInfoService {
    private final static String END_FLAG = "||";

    @Override
    public void generateSurfaceBaseInfo() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_DBSCDDY_" + DateUtils.localDateTime2String(now) + ".txt";
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        String content = "150622004499;不连沟煤矿;" + DateUtils.localDateTime2StringStyle2(now) + "~" +
                // 文件体
                bodyContent(now) +
                END_FLAG;
        FileUtils.genFile(filePath, content, "地表水基础信息");
    }

    @Override
    public void generateSurfaceCdss() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_DBSCDSS_" + DateUtils.localDateTime2String(now) + ".txt";
        StringBuilder content = new StringBuilder();
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        content.append("150622004499;不连沟煤矿;").append(DateUtils.localDateTime2StringStyle2(now)).append("~");
        // 文件体
        List<SurfaceBaseInfo> surfaceBaseInfos = queryChain().list();
        int randomLeaderIndex = ThreadLocalRandom.current().nextInt(surfaceBaseInfos.size());
        SurfaceBaseInfo surfaceBaseInfo = surfaceBaseInfos.get(randomLeaderIndex);
        content.append(cdssBodyContent(now, surfaceBaseInfo));
        content.append(END_FLAG);
        FileUtils.genFile(filePath, content.toString(), "地表水实时数据");
    }

    private StringBuilder cdssBodyContent(LocalDateTime now, SurfaceBaseInfo surfaceBaseInfo) {
        StringBuilder content = new StringBuilder();
        // 文件体
        content.append(surfaceBaseInfo.getId()).append(";")
                .append(StringUtils.doubleTypeString(0, 20)).append(";")
                .append(StringUtils.doubleTypeString(0, 20)).append(";")
                .append("0").append(";");
        content.append(DateUtils.localDateTime2StringStyle2(now));
        content.append("~");
        return content;
    }

    private String bodyContent(LocalDateTime localDateTime) {
        DateUtils.localDateTime2StringStyle3(localDateTime);
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(9);
        return "150622004499DBS" + randomAlphabetic + ";1;地表水" + randomAlphabetic + "号监测点;" + DateUtils.localDateTime2StringStyle3(localDateTime) + ";中矿安华;" + DateUtils.localDateTime2StringStyle3(localDateTime) + ";0502;m;4245615.60;36372560.60;1229.00~";
    }
}
