package cn.master.tauren.service.impl;

import cn.master.tauren.entity.SurfaceBaseInfo;
import cn.master.tauren.util.DateUtils;
import cn.master.tauren.util.FileUtils;
import cn.master.tauren.util.StringUtils;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.master.tauren.entity.GushingInfo;
import cn.master.tauren.mapper.GushingInfoMapper;
import cn.master.tauren.service.GushingInfoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static cn.master.tauren.constants.Constants.END_FLAG;
import static cn.master.tauren.constants.Constants.SENSOR_TYPE_YSL;

/**
 * 涌水量基础数据 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-11
 */
@Service
public class GushingInfoServiceImpl extends ServiceImpl<GushingInfoMapper, GushingInfo> implements GushingInfoService {

    @Override
    public void generateGushingInfo() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_YSLCDDY_" + DateUtils.localDateTime2String(now) + ".txt";
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        String content = "150622004499;不连沟煤矿;" + DateUtils.localDateTime2StringStyle2(now) + "~" +
                // 文件体
                bodyContent(now) +
                END_FLAG;
        FileUtils.genFile(filePath, content, "涌水量基础信息");
    }

    private String bodyContent(LocalDateTime localDateTime) {
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(9);
        return "150622004499YSL" + randomAlphabetic + ";" +
                "x车场" + randomAlphabetic + "号监测点;" +
                "煤矿" + randomAlphabetic + "采区;" +
                SENSOR_TYPE_YSL + ";" +
                "m³/h;" +
                "4245615.60;" + "36372560.60;" + "1229.00;" +
                DateUtils.localDateTime2StringStyle3(localDateTime) + "~";
    }

    @Override
    public void generateGushingCdss() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_YSLCDSS_" + DateUtils.localDateTime2String(now) + ".txt";
        StringBuilder content = new StringBuilder();
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        content.append("150622004499;不连沟煤矿;").append(DateUtils.localDateTime2StringStyle2(now)).append("~");
        // 文件体
        List<GushingInfo> gushingInfos = queryChain().list();
        int randomLeaderIndex = ThreadLocalRandom.current().nextInt(gushingInfos.size());
        GushingInfo surfaceBaseInfo = gushingInfos.get(randomLeaderIndex);
        content.append(cdssBodyContent(now, surfaceBaseInfo));
        content.append(END_FLAG);
        FileUtils.genFile(filePath, content.toString(), "涌水量实时数据");
    }

    private StringBuilder cdssBodyContent(LocalDateTime now, GushingInfo surfaceBaseInfo) {
        StringBuilder content = new StringBuilder();
        // 文件体
        content.append(surfaceBaseInfo.getId()).append(";")
                //.append(SENSOR_TYPE_YSL).append(";")
                .append("0").append(";");
        content.append(StringUtils.doubleTypeString(0, 100)).append(";");
        content.append(DateUtils.localDateTime2StringStyle2(now));
        content.append("~");
        return content;
    }
}
