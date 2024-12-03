package cn.master.tauren.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.master.tauren.entity.EmployeeInfo;
import cn.master.tauren.mapper.EmployeeInfoMapper;
import cn.master.tauren.service.EmployeeInfoService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author 11's papa
 * @since 1.0.0 2024-12-03
 */
@Service
public class EmployeeInfoServiceImpl extends ServiceImpl<EmployeeInfoMapper, EmployeeInfo>  implements EmployeeInfoService{

}
