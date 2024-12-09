package cn.master.tauren.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.master.tauren.entity.QuartzJobLog;
import cn.master.tauren.mapper.QuartzJobLogMapper;
import cn.master.tauren.service.QuartzJobLogService;
import org.springframework.stereotype.Service;

/**
 * 定时任务调度日志表 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-05
 */
@Service
public class QuartzJobLogServiceImpl extends ServiceImpl<QuartzJobLogMapper, QuartzJobLog>  implements QuartzJobLogService{

}
