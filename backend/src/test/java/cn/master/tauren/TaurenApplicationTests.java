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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TaurenApplicationTests {

    @Resource
    PasswordEncoder passwordEncoder;
    @Autowired
    private EmployeeInfoMapper mapper;
    @Test
    void contextLoads() {
        List<String> strings = new ArrayList<>();
        strings.add("测试");
        strings.add("厂家");
        if (strings.contains("厂家1")) {
            System.out.println(1);
        } else {
            System.out.println(2);
        }
    }

}
