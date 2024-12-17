package cn.master.tauren.service.impl;

import cn.master.tauren.entity.Precipitation;
import cn.master.tauren.mapper.PrecipitationMapper;
import cn.master.tauren.service.PrecipitationService;
import cn.master.tauren.util.DateUtils;
import cn.master.tauren.util.FileUtils;
import cn.master.tauren.util.StringUtils;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 降水量基础数据表 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-11
 */
@Service
public class PrecipitationServiceImpl extends ServiceImpl<PrecipitationMapper, Precipitation> implements PrecipitationService {

    private final static String END_FLAG = "||";

    @Override
    public void genPrecipitationCddyFile() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_JSLCDDY_" + DateUtils.localDateTime2String(now) + ".txt";
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        String content = "150622004499;不连沟煤矿;" + DateUtils.localDateTime2StringStyle2(now) + "~" +
                // 文件体
                bodyContent() +
                END_FLAG;
        FileUtils.genFile(filePath, content, "JSLCDDY");
    }

    @Override
    public void genPrecipitationCdssFile() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_JSLCDSS_" + DateUtils.localDateTime2String(now) + ".txt";
        StringBuilder content = new StringBuilder();
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        content.append("150622004499;不连沟煤矿;").append(DateUtils.localDateTime2StringStyle2(now)).append("~");
        // 文件体
        List<Precipitation> precipitationList = queryChain().list();
        int randomLeaderIndex = ThreadLocalRandom.current().nextInt(precipitationList.size());
        Precipitation leaderEmployeeInfo = precipitationList.get(randomLeaderIndex);
        content.append(cdssBodyContent(now, leaderEmployeeInfo, "0"));
        content.append(END_FLAG);
        FileUtils.genFile(filePath, content.toString(), fileName);
    }

    @Override
    public void genPrecipitationAlarmFile(String alarmFlag) {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_JSLCDSS_" + DateUtils.localDateTime2String(now) + ".txt";
        StringBuilder content = new StringBuilder();
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        content.append("150622004499;不连沟煤矿;").append(DateUtils.localDateTime2StringStyle2(now)).append("~");
        // 文件体
        List<Precipitation> precipitationList = queryChain().list();
        int randomLeaderIndex = ThreadLocalRandom.current().nextInt(precipitationList.size());
        Precipitation leaderEmployeeInfo = precipitationList.get(randomLeaderIndex);
        content.append(cdssBodyContent(now, leaderEmployeeInfo, alarmFlag));
        content.append(END_FLAG);
        FileUtils.genFile(filePath, content.toString(), fileName);
    }

    private StringBuilder cdssBodyContent(LocalDateTime now, Precipitation precipitation, String alarmFlag) {
        StringBuilder content = new StringBuilder();
        // 文件体
        Random random = new Random();
        String localed = DateUtils.localDateTime2StringStyle2(now.minusMinutes(random.nextInt(10, 30)));
        content.append(precipitation.getId()).append(";").append(localed).append(";").append(localed).append(";").append(localed).append(";");
        // todo 测点值和测点状态对应关系
        content.append(org.apache.commons.lang3.StringUtils.equals("0", alarmFlag) ? "0;" : "32;");
        // 测点值
        content.append(StringUtils.doubleTypeString(0, 20));
        content.append("~");
        return content;
    }

    private String bodyContent() {
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(12);
        return "150622004499" + randomAlphabetic + ";3;降雨量观测站" + randomAlphabetic + ";2023-11-10;山东科瑞特;2023-11-10;4245615.60;36372560.60;1229.00~";
    }
}
