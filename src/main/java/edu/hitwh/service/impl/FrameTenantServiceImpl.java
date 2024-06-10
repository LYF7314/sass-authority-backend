package edu.hitwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hitwh.dto.TenantAdminDTO;
import edu.hitwh.dto.TenantDTO;
import edu.hitwh.entity.Role;
import edu.hitwh.entity.Tenant;
import edu.hitwh.entity.User;
import edu.hitwh.mapper.FrameTenantMapper;
import edu.hitwh.service.IFrameRoleService;
import edu.hitwh.service.IFrameTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hitwh.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class FrameTenantServiceImpl extends ServiceImpl<FrameTenantMapper, Tenant> implements IFrameTenantService {

    @Resource
    private FrameTenantMapper frameTenantMapper;
    @Resource
    private IFrameRoleService frameRoleService;

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

    @Override
    public List<Tenant> searchTenant(String name, Integer state) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Tenant::getName,name);
        if(state != null){
            wrapper.eq(Tenant::getState,state);
        }
        List<Tenant> tenants = frameTenantMapper.selectList(wrapper);
        return tenants;
    }

    //TODO: Implement the initialize method
    @Override
    public boolean initialize(TenantAdminDTO tenantAdminDTO) {
        //create role
        Role role = new Role();
        role.setName(tenantAdminDTO.getRoleName());
        if(!frameRoleService.save(role))return false;

        //create user
        User user = new User();
        user.setAccount(tenantAdminDTO.getAccount());
        user.setUserName(tenantAdminDTO.getUserName());
        user.setPassword(tenantAdminDTO.getAccount());


        //update tenant
        Tenant tenant = new Tenant();
        tenant.setId(tenantAdminDTO.getId());
        tenant.setRoleName(tenantAdminDTO.getRoleName());
        tenant.setAccount(tenantAdminDTO.getAccount());
        tenant.setUserName(tenantAdminDTO.getUserName());
        frameTenantMapper.updateById(tenant);
        return true;
    }
}
