package cn.master.tauren.service.impl;

import cn.master.tauren.entity.HoleInfo;
import cn.master.tauren.mapper.HoleInfoMapper;
import cn.master.tauren.service.HoleInfoService;
import cn.master.tauren.util.DateUtils;
import cn.master.tauren.util.FileUtils;
import com.mybatisflex.spring.service.impl.ServiceImpl;
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

    private String realDataBodyContent(LocalDateTime now, HoleInfo holeInfo) {
        return holeInfo.getId() + ";" + "00000000;" + "10.00;26.00;" + DateUtils.localDateTime2StringStyle2(now) + "~";
    }
}
