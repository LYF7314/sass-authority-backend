package edu.hitwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hitwh.dto.TenantAdminDTO;
import edu.hitwh.dto.TenantDTO;
import edu.hitwh.entity.*;
import edu.hitwh.mapper.*;
import edu.hitwh.service.IFrameRoleService;
import edu.hitwh.service.IFrameTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hitwh.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyf ReubenRogar
 * @since 2024-06-06
 */
@Service
@Transactional
public class FrameTenantServiceImpl extends ServiceImpl<FrameTenantMapper, Tenant> implements IFrameTenantService {

    private FrameTenantMapper frameTenantMapper;
    private FrameRoleMapper frameRoleMapper;
    private FrameRolefunctionMapper frameRolefunctionMapper;
    private FrameTenantfunctionMapper frameTenantfunctionMapper;
    private FrameUserMapper frameUserMapper;
    private FrameUserroleMapper frameUserRoleMapper;
    private FrameUserfunctionMapper frameUserfunctionMapper;

    @Autowired
    public FrameTenantServiceImpl(FrameTenantMapper frameTenantMapper, FrameRoleMapper frameRoleMapper, FrameRolefunctionMapper frameRolefunctionMapper, FrameTenantfunctionMapper frameTenantfunctionMapper, FrameUserMapper frameUserMapper, FrameUserfunctionMapper frameUserfunctionMapper) {
        this.frameTenantMapper = frameTenantMapper;
        this.frameRoleMapper = frameRoleMapper;
        this.frameRolefunctionMapper = frameRolefunctionMapper;
        this.frameTenantfunctionMapper = frameTenantfunctionMapper;
        this.frameUserMapper = frameUserMapper;
        this.frameUserRoleMapper = frameUserRoleMapper;
        this.frameUserfunctionMapper = frameUserfunctionMapper;
    }

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
        role.setTenantId(tenantAdminDTO.getId());
        if(frameRoleMapper.insert(role)!=1)return false;

        //create user
        User user = new User();
        user.setAccount(tenantAdminDTO.getAccount());
        user.setUserName(tenantAdminDTO.getUserName());
        user.setPassword(tenantAdminDTO.getAccount());
        user.setTenantId(tenantAdminDTO.getId());
        if(frameUserMapper.insert(user)!=1)throw new RuntimeException("Failed to create user");

        //bind user and role
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        if(frameUserRoleMapper.insert(userRole)!=1)throw new RuntimeException("Failed to bind user and role");

        //bind role, user and functions
        List<Long> functionIds = frameTenantfunctionMapper.selectList(new LambdaQueryWrapper<TenantFunction>().eq(TenantFunction::getTenantId, tenantAdminDTO.getId()))
                .stream()
                .map(TenantFunction::getId)
                .toList();
        for (Long functionId : functionIds) {
            RoleFunction roleFunction = new RoleFunction();
            roleFunction.setRoleId(role.getId());
            roleFunction.setTenantFunctionId(functionId);
            if(frameRolefunctionMapper.insert(roleFunction)!=1)throw new RuntimeException("Failed to bind role and functions");
        }


        //update tenant
        Tenant tenant = new Tenant();
        tenant.setId(tenantAdminDTO.getId());
        tenant.setRoleName(tenantAdminDTO.getRoleName());
        tenant.setAccount(tenantAdminDTO.getAccount());
        tenant.setUserName(tenantAdminDTO.getUserName());
        if(frameTenantMapper.updateById(tenant) != 1)throw new RuntimeException("Failed to update tenant");
        return true;
    }

    public boolean isConflict(Tenant tenant){
        if(frameTenantMapper.exists(new LambdaQueryWrapper<Tenant>()
                .eq(Tenant::getName,tenant.getName())
                .or()
                .eq(Tenant::getCode,tenant.getCode()))){
            return true;
        }else return false;
    }

    @Override
    public boolean addTenant(Tenant tenant) {
        if(isConflict(tenant))return false;
        return save(tenant);
    }

    @Override
    public boolean updateTenant(Tenant tenant) {
        if(isConflict(tenant))return false;
        return false;
    }

    @Override
    public boolean existsTenant(Integer tenantId) {
        return frameTenantMapper.exists(new LambdaQueryWrapper<Tenant>().eq(Tenant::getId,tenantId));
    }
}
