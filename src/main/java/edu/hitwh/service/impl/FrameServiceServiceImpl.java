package edu.hitwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hitwh.dto.ServiceReferDTO;
import edu.hitwh.entity.Service;
import edu.hitwh.mapper.FrameServiceMapper;
import edu.hitwh.service.IFrameServiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hitwh.utils.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyf
 * @since 2024-06-06
 */
@org.springframework.stereotype.Service
public class FrameServiceServiceImpl extends ServiceImpl<FrameServiceMapper, Service> implements IFrameServiceService {

    @Resource
    private FrameServiceMapper serviceMapper;

    @Override
    public Result getServiceRefers() {
        List<ServiceReferDTO> ServiceReferDTOList = serviceMapper.selectList(new LambdaQueryWrapper<>())
                .stream()
                .map(ServiceReferDTO::new)
                .toList();
        if (ServiceReferDTOList.isEmpty()) {
            return Result.okWithMessage("Service not found");
        }
        return Result.ok(ServiceReferDTOList);
    }
}
