package cn.master.tauren.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Created by 11's papa on 12/09/2024
 **/
@Slf4j
@Component("customTask")
public class CustomTask {
    public void noParams() {
        log.info("执行无参方法");
    }
}
