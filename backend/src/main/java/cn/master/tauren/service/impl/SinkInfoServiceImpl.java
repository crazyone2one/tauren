package cn.master.tauren.service.impl;

import cn.master.tauren.entity.SinkInfo;
import cn.master.tauren.mapper.SinkInfoMapper;
import cn.master.tauren.service.SinkInfoService;
import cn.master.tauren.util.DateUtils;
import cn.master.tauren.util.FileUtils;
import cn.master.tauren.util.StringUtils;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static cn.master.tauren.constants.Constants.END_FLAG;

/**
 * 地表岩移基础数据 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-16
 */
@Service
public class SinkInfoServiceImpl extends ServiceImpl<SinkInfoMapper, SinkInfo> implements SinkInfoService {

    @Override
    public void generateSinkRealData() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_DBCXCDSS_" + DateUtils.localDateTime2String(now) + ".txt";
        StringBuilder content = new StringBuilder();
        String filePath = "/app/files/shfz/" + fileName;
        //String filePath = "E:/ftp/" + fileName;
        // 文件头
        content.append("150622004499;不连沟煤矿;").append(DateUtils.localDateTime2StringStyle2(now)).append("~");
        // 文件体
        List<SinkInfo> lists = queryChain().list();
        int randomLeaderIndex = ThreadLocalRandom.current().nextInt(lists.size());
        SinkInfo surfaceBaseInfo = lists.get(randomLeaderIndex);
        content.append(realDataBodyContent(now, surfaceBaseInfo));
        content.append(END_FLAG);
        FileUtils.genFile(filePath, content.toString(), "地表岩移实时数据");
    }

    private String realDataBodyContent(LocalDateTime now, SinkInfo info) {
        return info.getId() + ";" + info.getAreaName() + ";" + info.getPointId() + ";" + "0;" +
                StringUtils.doubleTypeString(0, 4245615) + ";" +
                StringUtils.doubleTypeString(0, 36372560) + ";" +
                StringUtils.doubleTypeString(0, 1229) + ";"
                + DateUtils.localDateTime2StringStyle2(now) + "~";
    }
}
