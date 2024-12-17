package cn.master.tauren;

import cn.master.tauren.entity.EmployeeInfo;
import cn.master.tauren.entity.User;
import cn.master.tauren.mapper.EmployeeInfoMapper;
import cn.master.tauren.mapper.UserMapper;
import cn.master.tauren.service.HoleInfoService;
import cn.master.tauren.util.DateUtils;
import cn.master.tauren.util.FileUtils;
import com.mybatisflex.core.query.QueryChain;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.master.tauren.constants.Constants.END_FLAG;

@SpringBootTest
class TaurenApplicationTests {

    @Resource
    PasswordEncoder passwordEncoder;
    @Autowired
    private HoleInfoService mapper;

    @Test
    void contextLoads() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_DBCXCDDY_" + DateUtils.localDateTime2String(now) + ".txt";
        //String filePath = "/app/files/shfz/" + fileName;
        String filePath = "E:/ftp/" + fileName;
        // 文件头
        String content = "150622004499;不连沟煤矿;" + DateUtils.localDateTime2StringStyle2(now) + "~" +
                // 文件体
                bodyContent(now) +
                END_FLAG;
        FileUtils.genFile(filePath, content, "钻孔应力测点基本信息");
        //mapper.generateHoleRealData();
    }

    private String bodyContent(LocalDateTime localDateTime) {
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(8);
        return "150622004499dbcx" + randomAlphabetic + ";" +
                randomAlphabetic + "采区地表沉陷区;1401.00;100.00;" +
                "4245615.60,4245615.60&" + "36372560.60,36372560.60&" + "1229.00,1229.00;" +
                "煤矿" + randomAlphabetic + "开采方法;" +
                randomAlphabetic + "号桩点;" +

                "4245615.60;" + "36372560.60;" + "1229.00;" +
                DateUtils.localDateTime2StringStyle3(localDateTime) + "~";
    }
}
