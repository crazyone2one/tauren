package cn.master.tauren;

import cn.master.tauren.entity.EmployeeInfo;
import cn.master.tauren.entity.User;
import cn.master.tauren.mapper.EmployeeInfoMapper;
import cn.master.tauren.mapper.UserMapper;
import com.mybatisflex.core.query.QueryChain;
import jakarta.annotation.Resource;
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

@SpringBootTest
class TaurenApplicationTests {

    @Resource
    PasswordEncoder passwordEncoder;
    @Autowired
    private EmployeeInfoMapper mapper;
    @Test
    void contextLoads() {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new Date(1734073558);
        String str = sdf.format(date);
        System.out.println(str);
    }

}
