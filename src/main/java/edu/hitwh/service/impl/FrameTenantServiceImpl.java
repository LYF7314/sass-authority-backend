package edu.hitwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hitwh.dto.TenantDTO;
import edu.hitwh.entity.Tenant;
import edu.hitwh.mapper.FrameTenantMapper;
import edu.hitwh.service.IFrameTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hitwh.utils.Result;
import org.springframework.stereotype.Service;

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
@Service
public class FrameTenantServiceImpl extends ServiceImpl<FrameTenantMapper, Tenant> implements IFrameTenantService {

    @Resource
    private FrameTenantMapper frameTenantMapper;

    @Override
    public Result getAllTenantDTOs() {
        List<TenantDTO> tenantDTOList = frameTenantMapper.selectList(new LambdaQueryWrapper<Tenant>())
                .stream()
                .map(TenantDTO::new)
                .toList();
        if (tenantDTOList.isEmpty()) {
            return Result.fail("No tenant found");
        }
        return Result.ok(tenantDTOList);
    }
}
