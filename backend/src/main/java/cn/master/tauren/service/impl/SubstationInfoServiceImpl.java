package cn.master.tauren.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.master.tauren.entity.SubstationInfo;
import cn.master.tauren.mapper.SubstationInfoMapper;
import cn.master.tauren.service.SubstationInfoService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-03
 */
@Service
public class SubstationInfoServiceImpl extends ServiceImpl<SubstationInfoMapper, SubstationInfo>  implements SubstationInfoService{

}
