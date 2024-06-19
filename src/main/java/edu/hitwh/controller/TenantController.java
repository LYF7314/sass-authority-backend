package edu.hitwh.controller;

import edu.hitwh.dto.*;
import edu.hitwh.entity.Tenant;
import edu.hitwh.entity.User;
import edu.hitwh.service.IFrameTenantService;
import edu.hitwh.utils.RedisConstants;
import edu.hitwh.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tenant")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TenantController {
    @Autowired
    public TenantController(IFrameTenantService frameTenantService){
        this.frameTenantService = frameTenantService;
    }

    private IFrameTenantService frameTenantService;


    /**
     * 创建租户
     * 提供必需的租户编码、租户名称、租户类型、状态、租户类型，非必需的邮箱、电话、地址、描述
     * @return 成功信息
     */
    @PostMapping("/add")
    public Result addTenant(@RequestBody Tenant tenant) {
        log.info("add tenant: {}",tenant.toString());
        tenant.setId(null);
        if(tenant.getCode() == null  ||
        tenant.getName() == null || tenant.getName().isBlank() ||
        tenant.getState() == null || tenant.getType() == null){
            return Result.fail("创建失败，请完善信息");
        }
        return frameTenantService.addTenant(tenant)?Result.ok():Result.fail("添加租户失败，租户名或编号重复");
    }

    /**
     * 获取所有租户信息
     * @return 租户详细列表
     */
    @GetMapping("/all")
    public Result getAllTenants(HttpSession session) {
        log.info("get all tenants");
        User user = (User)(session.getAttribute(RedisConstants.LOGIN_INFO_KEY));
        log.info("get all tenants-session: {}",user.getId());
        List<Tenant> tenants = frameTenantService.list();
        return tenants == null?Result.fail("获取租户失败"):Result.ok(tenants);
    }

    /**
     * 搜索租户
     * 提供租户名（必需）和状态
     * @return 符合搜索条件的租户
     */
    @GetMapping("/search")
    public Result searchTenant(SearchTenantDTO search){
        log.info("search tenant: {}",search.toString());
        List<Tenant> tenants = frameTenantService.searchTenant(search.getName(),search.getState());
        return tenants == null?Result.fail("无法搜索到符合条件的租户"):Result.ok(tenants);
    }

    /**
     * 更新租户
     * 租户id编号必需，其他信息若干
     * @return 成功信息
     */
    @PutMapping("/update")
    public Result updateTenant(@RequestBody Tenant tenant){
        log.info("update tenant: {}",tenant.toString());
        if(tenant.getId() == null){
            return Result.fail("请选择租户");
        }
        if(tenant.getName() != null && tenant.getName().isBlank() ||
                tenant.getState() != null && !Tenant.stateList.contains(tenant.getState())||
                tenant.getType() != null && !Tenant.typeList.contains(tenant.getType())){
            return Result.fail("请按规范填写信息");
        }
        log.info("update tenant: {}",tenant);
        log.info("tenant state"+tenant.getState()+" "+Tenant.stateList.contains(tenant.getState()));
        log.info("tenant type"+tenant.getState()+" "+Tenant.typeList.contains(tenant.getState()));
        return frameTenantService.updateTenant(tenant)?Result.ok():Result.fail("修改失败");
    }

    @GetMapping("/business")
    public Result tenantBusiness(Integer tenantId){
        log.info("get tenant business: {}",tenantId);
        if(tenantId == null)return Result.fail("请选择租户");
        if(frameTenantService.existsTenant(tenantId)){
            return Result.ok(false);
        }else {
            return Result.fail("租户不存在");
        }
    }

    /**
     * 删除租户和有关信息
     * 提供租户id
     * @param tenantId
     * @return 成功信息
     */
    @DeleteMapping("/delete")
    public Result deleteTenant(Integer tenantId){
        log.info("delete tenant: {}",tenantId);
        if (tenantId == null)return Result.fail("请选择租户");
        return frameTenantService.removeById(tenantId)?Result.ok():Result.fail("删除失败");
    }

    /**
     * 初始化租户，创建一个拥有租户所有权限的角色和有此角色的用户
     * 制定租户id、初始角色名、登录用账户名（密码同）、用户名
     * @return 成功信息
     */
    @PostMapping("/initialize")
    public Result initializeTenant(@RequestBody TenantAdminDTO tenant){
        log.info("initialize tenant: {}",tenant.toString());
        if(tenant.getTenantId() == null)return Result.fail("请选择租户");
        if(tenant.getRoleName() == null || tenant.getRoleName().isBlank() || tenant.getUserName() == null || tenant.getUserName().isBlank() || tenant.getAccount() == null || tenant.getAccount().isBlank()){
            return Result.fail("请填写完整信息");
        }
        try{
            return frameTenantService.initialize(tenant) ? Result.ok() : Result.fail("初始化失败");
        }catch (RuntimeException e){
            return Result.fail(e.getMessage());
        }

    }

    /**
     * 获取租户的功能
     * @param tenantId
     * @return
     */
    @GetMapping("/functions")
    public Result getTenantFunctions(Long tenantId){
        log.info("get tenant functions: {}",tenantId);
        if(tenantId == null)return Result.fail("请选择租户");
        List<FunctionNode> functions = frameTenantService.getTenantFunctions(tenantId);
        return functions == null?Result.fail("查找该租户功能失败"):Result.ok(functions);
    }

    /**
     * 修改租户功能
     * 存疑
     * @return
     */
    @PostMapping("/function/distribute")
    public Result distributeFunction(@RequestBody TenantFunctionDTO tenantFunctionDTO){
        log.info("distribute function: {}",tenantFunctionDTO.toString());
        if(tenantFunctionDTO.getTenantId()==null)return Result.fail("请选择租户");
        return frameTenantService.distributeFunction(tenantFunctionDTO)?Result.ok():Result.fail("分配失败");
    }
}
