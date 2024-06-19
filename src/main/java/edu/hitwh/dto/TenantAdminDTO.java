package edu.hitwh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TenantAdminDTO {
    //租户id
    private Long tenantId;
    //初始化角色名，创建具有租户所有权限的角色
    private String roleName;
    //登录用账户名
    private String account;
    //用户名
    private String userName;
}
