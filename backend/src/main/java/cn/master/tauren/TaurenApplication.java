package cn.master.tauren;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author the2n
 */
//@EnableScheduling
@SpringBootApplication
@MapperScan("cn.master.tauren.mapper")
public class TaurenApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaurenApplication.class, args);
    }

}
