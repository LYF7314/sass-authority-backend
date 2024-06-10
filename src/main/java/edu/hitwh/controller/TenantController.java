package edu.hitwh.controller;

import edu.hitwh.dto.TenantAdminDTO;
import edu.hitwh.dto.TenantDTO;
import edu.hitwh.entity.Tenant;
import edu.hitwh.service.IFrameTenantService;
import edu.hitwh.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/tenant")
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
        tenant.setId(null);
        if(tenant.getCode() == null ||tenant.getCode().isBlank() ||
        tenant.getName() == null || tenant.getName().isBlank() ||
        tenant.getState() == null || tenant.getType() == null){
            return Result.fail("创建失败，请完善信息");
        }
        return frameTenantService.save(tenant)?Result.fail("添加租户失败"):Result.ok();
    }

    /**
     * 获取所有租户信息
     * @return 租户详细列表
     */
    @GetMapping("/all")
    public Result getAllTenants() {
        List<Tenant> tenants = frameTenantService.list();
        return tenants == null?Result.fail("获取租户失败"):Result.ok(tenants);
    }

    /**
     * 搜索租户
     * 提供租户名（必需）和状态
     * @return 符合搜索条件的租户
     */
    @GetMapping("/search")
    public Result searchTenant(@RequestParam String name,@RequestParam Integer state) {
        if(name == null || name.isBlank())return Result.fail("请输入租户名");
        List<Tenant> tenants = frameTenantService.searchTenant(name,state);
        return tenants == null?Result.fail("无法搜索到符合条件的租户"):Result.ok(tenants);
    }

    /**
     * 更新租户
     * 租户id编号必需，其他信息若干
     * @return 成功信息
     */
    @PutMapping("/update")
    public Result updateTenant(@RequestBody Tenant tenant){
        if(tenant.getId() == null){
            return Result.fail("请选择租户");
        }
        return frameTenantService.updateById(tenant)?Result.ok():Result.fail("修改失败");
    }

    @GetMapping("/business")
    public Result tenantBusiness(@RequestParam Integer tenantId){
        if(tenantId == null)return Result.fail("请选择租户");
        return Result.ok(true);
    }

    /**
     * 删除租户和有关信息
     * 提供租户id
     * @param tenantId
     * @return 成功信息
     */
    @DeleteMapping("/delete")
    public Result deleteTenant(@PathVariable Integer tenantId){
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
        if(tenant.getId() == null)return Result.fail("请选择租户");
        if(tenant.getRoleName() == null || tenant.getRoleName().isBlank() || tenant.getUserName() == null || tenant.getUserName().isBlank() || tenant.getAccount() == null || tenant.getAccount().isBlank()){
            return Result.fail("请填写完整信息");
        }
        return frameTenantService.initialize(tenant)?Result.ok():Result.fail("初始化失败");
    }

    /**
     * 分配功能给租户
     * 存疑
     * @return
     */
    @PostMapping("/function/distribute")
    public Result distributeFunction(){
        return Result.fail("Not implement");
    }
}
