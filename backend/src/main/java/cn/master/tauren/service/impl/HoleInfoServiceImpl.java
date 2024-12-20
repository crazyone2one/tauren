package cn.master.tauren.service.impl;

import cn.master.tauren.entity.HoleInfo;
import cn.master.tauren.entity.SinkInfo;
import cn.master.tauren.mapper.HoleInfoMapper;
import cn.master.tauren.service.HoleInfoService;
import cn.master.tauren.util.DateUtils;
import cn.master.tauren.util.FileUtils;
import cn.master.tauren.util.StringUtils;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static cn.master.tauren.constants.Constants.END_FLAG;

/**
 * 长观孔测点基本信息 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-16
 */
@Service
public class HoleInfoServiceImpl extends ServiceImpl<HoleInfoMapper, HoleInfo> implements HoleInfoService {

    @Override
    public void generateHoleInfo() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_CGKCDDY_" + DateUtils.localDateTime2String(now) + ".txt";
        StringBuilder content = new StringBuilder();
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        content.append("150622004499;不连沟煤矿;").append(DateUtils.localDateTime2StringStyle2(now)).append("~");
        // 文件体
        content.append(baseBodyContent(now));
        content.append(END_FLAG);
        FileUtils.genFile(filePath, content.toString(), "长观孔基础数据");
    }

    private String baseBodyContent(LocalDateTime now) {
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(9);
        return "150622004499MN" + randomAlphabetic + ";0502;" +
                "水文孔"+randomAlphabetic + ";" +
                randomAlphabetic + "上段;" +
                StringUtils.doubleTypeString(50, 100) + ";" + StringUtils.doubleTypeString(50, 100) + ";" +
                StringUtils.doubleTypeString(50, 100) + ";" + StringUtils.doubleTypeString(50, 100) + ";" +
                "4245615.60;36372560.60;1229.00;" +
                DateUtils.localDateTime2StringStyle3(now) + "~";
    }

    @Override
    public void generateHoleRealData() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_CGKCDSS_" + DateUtils.localDateTime2String(now) + ".txt";
        StringBuilder content = new StringBuilder();
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        content.append("150622004499;不连沟煤矿;").append(DateUtils.localDateTime2StringStyle2(now)).append("~");
        // 文件体
        List<HoleInfo> lists = queryChain().list();
        int randomLeaderIndex = ThreadLocalRandom.current().nextInt(lists.size());
        HoleInfo surfaceBaseInfo = lists.get(randomLeaderIndex);
        content.append(realDataBodyContent(now, surfaceBaseInfo));
        content.append(END_FLAG);
        FileUtils.genFile(filePath, content.toString(), "长观孔实时数据");
    }

    @Override
    public void generateHoleAlarmData() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_CGKCDYC_" + DateUtils.localDateTime2String(now) + ".txt";
        StringBuilder content = new StringBuilder();
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        content.append("150622004499;不连沟煤矿;").append(DateUtils.localDateTime2StringStyle2(now)).append("~");
        // 文件体
        List<HoleInfo> lists = queryChain().list();
        int randomLeaderIndex = ThreadLocalRandom.current().nextInt(lists.size());
        HoleInfo surfaceBaseInfo = lists.get(randomLeaderIndex);
        content.append(alarmDataBodyContent(now, surfaceBaseInfo));
        content.append(END_FLAG);
        FileUtils.genFile(filePath, content.toString(), "长观孔测点异常数据");
    }

    private String alarmDataBodyContent(LocalDateTime now, HoleInfo info) {
        return info.getId() + ";" + info.getType() + ";" + info.getPosition() + ";"
                + "1;"
                + "实时预警3.9676m>涨阈值（蓝）3.00m;"
                + DateUtils.localDateTime2StringStyle2(now) + ";"
                + "4;"
                + "传感器故障导致数据异常，" + RandomStringUtils.randomAlphabetic(9) + ";"
                + "zs;"
                + DateUtils.localDateTime2StringStyle2(now.plusMinutes(10)) + ";"
                + DateUtils.localDateTime2StringStyle2(now.plusMinutes(10)) + "~";
    }

    private String realDataBodyContent(LocalDateTime now, HoleInfo holeInfo) {
        return holeInfo.getId() + ";" + "00000000;"
                + StringUtils.doubleTypeString(0, 30) + ";"
                + StringUtils.doubleTypeString(0, 30) + ";"
                + DateUtils.localDateTime2StringStyle2(now) + "~";
    }
}
