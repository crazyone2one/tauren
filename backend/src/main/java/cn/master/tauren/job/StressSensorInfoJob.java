package cn.master.tauren.job;

import cn.master.tauren.util.DateUtils;
import cn.master.tauren.util.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static cn.master.tauren.constants.Constants.END_FLAG;

/**
 * @author Created by 11's papa on 12/16/2024
 **/
@DisallowConcurrentExecution
public class StressSensorInfoJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));
        String fileName = "150622004499_ZKYL_" + DateUtils.localDateTime2String(now) + ".txt";
        //String filePath = "/app/files/shfz/" + fileName;
        String filePath = "E:/ftp/" + fileName;
        // 文件头
        String content = "150622004499;不连沟煤矿;" + DateUtils.localDateTime2StringStyle2(now) + "~" +
                // 文件体
                bodyContent(now) +
                END_FLAG;
        FileUtils.genFile(filePath, content, "钻孔应力测点基本信息");
    }

    private String bodyContent(LocalDateTime localDateTime) {
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(8);
        return "150622004499ZKYL" + randomAlphabetic + ";" +
                randomAlphabetic + "巷道;1401;" +
                "煤矿" + randomAlphabetic + "巷道;" +
                randomAlphabetic + "工作面;" + DateUtils.localDateTime2StringStyle3(localDateTime) + ";" +
                "4245615.60;" + "36372560.60;" + "1229.00;" +
                "10;30" + "~";
    }
}
