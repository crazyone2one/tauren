package cn.master.tauren.service.impl;

import cn.master.tauren.entity.GushingInfo;
import cn.master.tauren.util.DateUtils;
import cn.master.tauren.util.FileUtils;
import cn.master.tauren.util.StringUtils;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.master.tauren.entity.PumpInfo;
import cn.master.tauren.mapper.PumpInfoMapper;
import cn.master.tauren.service.PumpInfoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static cn.master.tauren.constants.Constants.END_FLAG;
import static cn.master.tauren.constants.Constants.SENSOR_TYPE_YSL;

/**
 * 疏放水钻孔基础数据 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-12
 */
@Service
public class PumpInfoServiceImpl extends ServiceImpl<PumpInfoMapper, PumpInfo> implements PumpInfoService {

    @Override
    public void generatePumpInfo() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_SFSCDDY_" + DateUtils.localDateTime2String(now) + ".txt";
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        StringBuilder content = new StringBuilder();
        content.append("150622004499;不连沟煤矿;").append(DateUtils.localDateTime2StringStyle2(now)).append("~");
        // 文件体
        Random random = new Random();
        int nextInt = random.nextInt(5);
        for (int i = 0; i < nextInt; i++) {
            content.append(bodyContent(now, i));
        }

        content.append(END_FLAG);
        FileUtils.genFile(filePath, content.toString(), "疏（放）水监测测点基础信息");
    }

    private String bodyContent(LocalDateTime localDateTime, int index) {
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(9);
        StringBuilder content = new StringBuilder();
        content.append("150622004499SFS").append(randomAlphabetic).append(";");
        content.append("水文孔").append(index).append(";");
        content.append("m;");
        content.append("延安组;");
        content.append(StringUtils.doubleTypeString(0, 20)).append(";");
        content.append(StringUtils.doubleTypeString(0, 120)).append(";");
        content.append(StringUtils.doubleTypeString(0, 120)).append(";");
        content.append(StringUtils.doubleTypeString(0, 120)).append(";");
        content.append("4245615.60;" + "36372560.60;" + "1229.00;");
        content.append(StringUtils.doubleTypeString(0, 2)).append(";");
        content.append(StringUtils.doubleTypeString(0, 50)).append(";");
        content.append(DateUtils.localDateTime2StringStyle2(localDateTime)).append("~");
        return content.toString();
    }

    @Override
    public void generatePumpCdss() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_SFSCDSS_" + DateUtils.localDateTime2String(now) + ".txt";
        StringBuilder content = new StringBuilder();
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        content.append("150622004499;不连沟煤矿;").append(DateUtils.localDateTime2StringStyle2(now)).append("~");
        // 文件体
        List<PumpInfo> pumpInfos = queryChain().list();
        int randomLeaderIndex = ThreadLocalRandom.current().nextInt(pumpInfos.size());
        PumpInfo pumpInfo = pumpInfos.get(randomLeaderIndex);
        content.append(cdssBodyContent(now, pumpInfo));
        // 文件结束标记
        content.append(END_FLAG);
        FileUtils.genFile(filePath, content.toString(), "疏（放）水实时数据");
    }

    private StringBuilder cdssBodyContent(LocalDateTime now, PumpInfo pumpInfo) {
        StringBuilder content = new StringBuilder();
        content.append(pumpInfo.getId()).append(";");
        content.append(pumpInfo.getInstallLocation()).append(";");
        content.append(StringUtils.doubleTypeString(0, 11)).append(";");
        content.append(DateUtils.localDateTime2StringStyle2(now));
        content.append("~");
        return content;
    }
}
