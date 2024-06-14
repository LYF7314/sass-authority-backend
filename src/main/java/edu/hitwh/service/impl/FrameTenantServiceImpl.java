package edu.hitwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hitwh.dto.FunctionNode;
import edu.hitwh.dto.TenantAdminDTO;
import edu.hitwh.dto.TenantDTO;
import edu.hitwh.dto.TenantFunctionDTO;
import edu.hitwh.entity.*;
import edu.hitwh.mapper.*;
import edu.hitwh.service.IFrameTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hitwh.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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


    private FrameFunctionMapper frameFunctionMapper;

    @Autowired
    public FrameTenantServiceImpl(FrameTenantMapper frameTenantMapper, FrameRoleMapper frameRoleMapper, FrameRolefunctionMapper frameRolefunctionMapper, FrameTenantfunctionMapper frameTenantfunctionMapper, FrameUserMapper frameUserMapper,FrameFunctionMapper frameFunctionMapper,FrameUserroleMapper frameUserRoleMapper) {
        this.frameTenantMapper = frameTenantMapper;
        this.frameRoleMapper = frameRoleMapper;
        this.frameRolefunctionMapper = frameRolefunctionMapper;
        this.frameTenantfunctionMapper = frameTenantfunctionMapper;
        this.frameUserMapper = frameUserMapper;
        this.frameUserRoleMapper = frameUserRoleMapper;
        this.frameFunctionMapper = frameFunctionMapper;
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
        if(frameRoleMapper.exists(new LambdaQueryWrapper<Role>().eq(Role::getName,role.getName())))throw new RuntimeException("Role already exists");
        if(frameRoleMapper.insert(role)!=1)return false;

        //create user
        User user = new User();
        user.setAccount(tenantAdminDTO.getAccount());
        user.setUserName(tenantAdminDTO.getUserName());
        user.setPassword(tenantAdminDTO.getAccount());
        user.setTenantId(tenantAdminDTO.getId());
        if(frameUserMapper.exists(new LambdaQueryWrapper<User>().eq(User::getAccount,tenantAdminDTO.getAccount())))throw new RuntimeException("Account already exists");
        if(frameUserMapper.insert(user)!=1)throw new RuntimeException("Failed to create user");

        //bind user and role
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        if(frameUserRoleMapper.insert(userRole)!=1)throw new RuntimeException("Failed to bind user and role");

        //bind role, user and functions
        List<Long> functionIds = frameTenantfunctionMapper.selectList(new LambdaQueryWrapper<TenantFunction>().eq(TenantFunction::getTenantId, tenantAdminDTO.getId()).select(TenantFunction::getId))
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
                .ne(Tenant::getId,tenant.getId())
                .eq(Tenant::getName,tenant.getName())
                .or()
                .eq(Tenant::getCode,tenant.getCode())
                .ne(Tenant::getId,tenant.getId()))){
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
        return updateById(tenant);
    }

    @Override
    public boolean existsTenant(Integer tenantId) {
        return frameTenantMapper.exists(new LambdaQueryWrapper<Tenant>().eq(Tenant::getId,tenantId));
    }

    @Override
    public List<FunctionNode> getTenantFunctions(Long tenantId) {
        List<Long> functionIds = frameTenantfunctionMapper.selectList(new LambdaQueryWrapper<TenantFunction>().eq(TenantFunction::getTenantId, tenantId).select(TenantFunction::getFunctionId))
                .stream()
                .map(TenantFunction::getFunctionId)
                .toList();
        List<Function> functions = frameFunctionMapper.selectList(new LambdaQueryWrapper<Function>().in(Function::getId, functionIds).select(Function::getId, Function::getName, Function::getParentId,  Function::getIsLeaf, Function::getOrder));
        Map<Long,FunctionNode> functionMap = new HashMap<>();
        for (Function function : functions) {
            functionMap.put(function.getId(),new FunctionNode(function));
        }
        List<FunctionNode> finalFunctions = new ArrayList<>();
        for (Function function : functions) {
            if(function.getParentId() != null){
                FunctionNode parent = functionMap.get(function.getParentId());
                if(parent == null){
                    completeTenantFunction(tenantId);
                    return getTenantFunctions(tenantId);
                }
                functionMap.get(function.getParentId()).getChildren().add(functionMap.get(function.getId()));
            }else{
                finalFunctions.add(functionMap.get(function.getId()));
            }
        }
        //log.error(functionMap.toString());
        if(finalFunctions.get(0) == null)return new ArrayList<>();
        return finalFunctions.get(0).getChildren();
    }



    public void completeTenantFunction(Long tenantId){
        List<Long> functionIds = frameTenantfunctionMapper.selectList(new LambdaQueryWrapper<TenantFunction>().eq(TenantFunction::getTenantId, tenantId).select(TenantFunction::getFunctionId))
                .stream()
                .map(TenantFunction::getFunctionId)
                .toList();
        List<Function> functionList = frameFunctionMapper.selectList(new LambdaQueryWrapper<Function>().in(Function::getId, functionIds).select(Function::getId, Function::getParentId));
        Map< Long,Function> functions = new HashMap<>();
        for (Function function : functionList) {
            functions.put(function.getId(),function);
        }
        List<Long> missingParentIds = new ArrayList<>();
        log.error(functionIds.toString());
        while(!functionList.isEmpty()) {
            List<Function> parentFunctions = new ArrayList<>();
            for (Function function : functionList) {
                if (function.getParentId() != null && !functions.containsKey(function.getParentId())) {
                    missingParentIds.add(function.getParentId());
                    Function parent = frameFunctionMapper.selectOne(new LambdaQueryWrapper<Function>().eq(Function::getId, function.getParentId()).select(Function::getId, Function::getParentId));
                    functions.put(parent.getId(), parent);
                    parentFunctions.add(parent);
                }

            }
            functionList = parentFunctions;
        }
        for (Long missingParentId : missingParentIds) {
            frameTenantfunctionMapper.insert(new TenantFunction(tenantId,missingParentId));
        }
    }

    @Override
    public boolean distributeFunction(TenantFunctionDTO tenantFunctionDTO) {
        List<Long> functionIds = new ArrayList<>(frameTenantfunctionMapper.selectList(new LambdaQueryWrapper<TenantFunction>().eq(TenantFunction::getTenantId, tenantFunctionDTO.getTenantId()).select(TenantFunction::getFunctionId))
                .stream()
                .map(TenantFunction::getFunctionId)
                .toList());
        List<Long> newFunctionIds = new ArrayList<>();
        List<FunctionNode> newFunctions = Arrays.asList(tenantFunctionDTO.getFunctions());
        newFunctionIds.add(Long.reverse(0L));
        while (!newFunctions.isEmpty()){
            List<FunctionNode> childFunctions = new ArrayList<>();
            for (FunctionNode newFunction : newFunctions) {
                if(newFunctionIds.contains(newFunction.getId()))newFunctionIds.add(newFunction.getId());
                if(newFunction.getChildren() != null){
                    childFunctions.addAll(newFunction.getChildren());
                }
            }
            newFunctions = childFunctions;
        }
        log.error(newFunctionIds.toString());
        log.error(functionIds.toString());
        for (Long newFunctionId : newFunctionIds) {
            if(!functionIds.contains(newFunctionId)){
                frameTenantfunctionMapper.insert(new TenantFunction(tenantFunctionDTO.getTenantId(),newFunctionId));
            }else{
                functionIds.remove(newFunctionId);
            }
        }
        for (Long functionId : functionIds) {
            frameTenantfunctionMapper.delete(new LambdaQueryWrapper<TenantFunction>().eq(TenantFunction::getTenantId,tenantFunctionDTO.getTenantId()).eq(TenantFunction::getFunctionId,functionId));
        }
        return true;
    }
}
