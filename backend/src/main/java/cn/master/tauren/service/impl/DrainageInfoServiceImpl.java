package cn.master.tauren.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.master.tauren.entity.DrainageInfo;
import cn.master.tauren.mapper.DrainageInfoMapper;
import cn.master.tauren.service.DrainageInfoService;
import org.springframework.stereotype.Service;

/**
 * 排水量基础数据 服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-12
 */
@Service
public class DrainageInfoServiceImpl extends ServiceImpl<DrainageInfoMapper, DrainageInfo>  implements DrainageInfoService{

}
