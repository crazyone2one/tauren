package cn.master.tauren.service.impl;

import cn.master.tauren.entity.StressSensor;
import cn.master.tauren.mapper.StressSensorMapper;
import cn.master.tauren.service.StressSensorService;
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
 * 钻孔应力测点基本信息 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-17
 */
@Service
public class StressSensorServiceImpl extends ServiceImpl<StressSensorMapper, StressSensor> implements StressSensorService {

    @Override
    public void stressSensorInfo() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_ZKYL_" + DateUtils.localDateTime2String(now) + ".txt";
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        String content = "150622004499;不连沟煤矿;" + DateUtils.localDateTime2StringStyle2(now) + "~" +
                // 文件体
                bodyContent(now)
                + END_FLAG;
        FileUtils.genFile(filePath, content, "钻孔应力测点基本信息[" + fileName + "]");
    }

    private String bodyContent(LocalDateTime localDateTime) {
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(8);
        return "150622004499ZKYL" + randomAlphabetic + ";"
                + randomAlphabetic + "巷道;"
                + "1201;"
                + "距离" + randomAlphabetic + "巷道x米;"
                + StringUtils.doubleTypeString(50, 300) + ";"
                + DateUtils.localDateTime2StringStyle3(localDateTime) + ";"
                + "4245615.60;" + "36372560.60;" + "1229.00;"
                + "X;"
                + "12&10&100"
                + "~";
    }

    @Override
    public void stressSensorCdss() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_ZKSS_" + DateUtils.localDateTime2String(now) + ".txt";
        StringBuilder content = new StringBuilder();
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        content.append("150622004499;不连沟煤矿;").append(DateUtils.localDateTime2StringStyle2(now)).append("~");
        // 文件体
        List<StressSensor> lists = queryChain().list();
        int randomLeaderIndex = ThreadLocalRandom.current().nextInt(lists.size());
        StressSensor stressSensor = lists.get(randomLeaderIndex);
        content.append(cdssContent(now, stressSensor));
        content.append(END_FLAG);
        FileUtils.genFile(filePath, content.toString(), "钻孔应力测点实时数据[" + fileName + "]");
    }

    private String cdssContent(LocalDateTime now, StressSensor stressSensor) {
        return stressSensor.getId() + ";"
                + stressSensor.getPointType() + ";"
                + stressSensor.getPointName() + ";"
                + StringUtils.doubleTypeString(0, 10) + ";"
                + "0;"
                + DateUtils.localDateTime2StringStyle2(now)
                + "~";
    }

    @Override
    public void stressSensorAlarm() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_ZKYC_" + DateUtils.localDateTime2String(now) + ".txt";
        StringBuilder content = new StringBuilder();
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        content.append("150622004499;不连沟煤矿;").append(DateUtils.localDateTime2StringStyle2(now)).append("~");
        // 文件体
        List<StressSensor> lists = queryChain().list();
        int randomLeaderIndex = ThreadLocalRandom.current().nextInt(lists.size());
        StressSensor stressSensor = lists.get(randomLeaderIndex);
        content.append(alarmBodyContent(now, stressSensor));
        content.append(END_FLAG);
        FileUtils.genFile(filePath, content.toString(), "钻孔应力测点异常数据[" + fileName + "]");
    }

    private String alarmBodyContent(LocalDateTime now, StressSensor stressSensor) {
        return stressSensor.getId() + ";"
                + stressSensor.getPointType() + ";"
                + stressSensor.getPointName() + ";"
                + "1;"
                + DateUtils.localDateTime2StringStyle2(now.minusMinutes(5)) + ";"
                + DateUtils.localDateTime2StringStyle2(now) + ";"
                + StringUtils.doubleTypeString(50, 100) + ";"
                + DateUtils.localDateTime2StringStyle2(now.minusMinutes(4)) + ";"
                + StringUtils.doubleTypeString(30, 50) + ";"
                + DateUtils.localDateTime2StringStyle2(now.minusMinutes(2)) + ";"
                + StringUtils.doubleTypeString(10, 20) + ";"
                + DateUtils.localDateTime2StringStyle2(now.minusMinutes(1))
                + "~";
    }
}
