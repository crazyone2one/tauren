package cn.master.tauren.service.impl;

import cn.master.tauren.entity.AreaInfo;
import cn.master.tauren.entity.EmployeeInfo;
import cn.master.tauren.entity.SubstationInfo;
import cn.master.tauren.service.PersonnelRealTimeBehavior;
import cn.master.tauren.util.DateUtils;
import com.mybatisflex.core.query.QueryChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static cn.master.tauren.util.DateUtils.localDateTime2StringStyle2;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
@Slf4j
@Service
public class PersonnelRealTimeBehaviorImpl implements PersonnelRealTimeBehavior {
    private final static String END_FLAG = "||";
    LocalDateTime now = LocalDateTime.now(ZoneOffset.of("+8"));

    /**
     * 领导带班信息
     *
     * @return java.lang.String
     */
    private String leaderInfo(EmployeeInfo employeeInfo) {
        return localDateTime2StringStyle2(now) + ";297;" + employeeInfo.getPersonCode() + "/" + employeeInfo.getPersonName() + ";~";
    }

    /**
     * 员工轨迹信息
     *
     * @return java.lang.String
     */
    private String employeeBehaviorInfo(EmployeeInfo employeeInfo) {
        String empBaseInfo = employeeInfo.getPersonCode() + ";" + employeeInfo.getPersonName() + ";";
        String inDate = localDateTime2StringStyle2(now);
        StringBuilder content = new StringBuilder();
        content.append(empBaseInfo);
        // 出入井信息
        String inOrOut = "1;" + inDate + ";xxxx-xx-xx xx:xx:xx;";
        content.append(inOrOut);
        // 行为轨迹
        // 查询区域数据信息
        List<AreaInfo> areaInfos = QueryChain.of(AreaInfo.class).list();
        // 区域信息
        int count = 2;
        for (int i = 0; i < count; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(areaInfos.size());
            AreaInfo areaInfo = areaInfos.get(randomIndex);
            content.append(areaInfo.getAreaCode()).append(";").append(localDateTime2StringStyle2(now.plusMinutes(10))).append(";");
            areaInfos.removeIf(next -> next.getAreaCode().equals(areaInfo.getAreaCode()));
        }
        // 行为轨迹分站、时间集合
        // 查询基站信息
        List<SubstationInfo> substationInfos = QueryChain.of(SubstationInfo.class).list();
        //int collectCount = random.nextInt(10);
        int collectCount = 20;
        LocalDateTime tmpNowDate = LocalDateTime.now(ZoneOffset.of("+8"));
        for (int i = 0; i < collectCount; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(substationInfos.size());
            SubstationInfo substationInfo = substationInfos.get(randomIndex);
            // 150622B001200020009206AI&2024-12-02 08:10:37,150622B001200020009206BI&2024-12-02 08:11:05
            content.append(substationInfo.getStationCode()).append("&").append(localDateTime2StringStyle2(tmpNowDate)).append(",");
            substationInfos.removeIf(next -> next.getStationCode().equals(substationInfo.getStationCode()));
            tmpNowDate = tmpNowDate.plusMinutes(3);
        }
        content.deleteCharAt(content.length() - 1);
        content.append("~");
        return content.toString();
    }

    @Override
    public void genPersonnelFile() {
        StringBuilder content = new StringBuilder();
        List<EmployeeInfo> employeeInfos = QueryChain.of(EmployeeInfo.class).list();
        // 领导信息
        List<EmployeeInfo> leaderOfEmployee = employeeInfos.stream().filter(emp ->
                        Objects.nonNull(emp.getWhetherLeader()) && emp.getWhetherLeader().equals(true))
                .toList();
        int randomLeaderIndex = ThreadLocalRandom.current().nextInt(leaderOfEmployee.size());
        EmployeeInfo leaderEmployeeInfo = leaderOfEmployee.get(randomLeaderIndex);
        content.append(leaderInfo(leaderEmployeeInfo));
        // 员工信息
        Random random = new Random();
        int empCount = random.nextInt(50);
        //int empCount = 10;
        for (int i = 0; i < empCount; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(employeeInfos.size());
            EmployeeInfo employeeInfo = employeeInfos.get(randomIndex);
            boolean flag = employeeInfo.getPersonName().contains("厂家")
                    || employeeInfo.getPersonName().contains("贵宾")
                    || employeeInfo.getPersonName().contains("测试");
            if (flag) {
                continue;
            }
            content.append(employeeBehaviorInfo(employeeInfo));
            // 拼装之后将该员工从tmp中移除
            employeeInfos.removeIf(next -> next.getPersonCode().equals(employeeInfo.getPersonCode()));
        }

        content.append(END_FLAG);
        // 20241202160508
        String filePath = "E:/ftp/" + "150622B0012000200092_RYSS_" + DateUtils.localDateTime2String(now) + ".txt";
        FileWriter fw = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (newFile) {
                    log.info("file created");
                }
            }
            fw = new FileWriter(filePath);
            fw.write(content.toString());
        } catch (Exception e) {
            log.error("", e);
        } finally {
            try {
                assert fw != null;
                fw.close();
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }
}
