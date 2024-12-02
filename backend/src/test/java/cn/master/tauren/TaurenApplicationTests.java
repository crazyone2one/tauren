package cn.master.tauren;

import cn.master.tauren.entity.User;
import cn.master.tauren.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class TaurenApplicationTests {

    @Resource
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        User build = User.builder().build();
        build.setEnabled(false);
        build.setUsername("admin1");
        build.setNickname("admin1");
        build.setDeleted(false);
        build.setPassword(passwordEncoder.encode("123456"));
        userMapper.insert(build);
    }

}
