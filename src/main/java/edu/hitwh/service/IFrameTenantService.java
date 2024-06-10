package edu.hitwh.service;

import edu.hitwh.dto.TenantAdminDTO;
import edu.hitwh.entity.Tenant;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.hitwh.utils.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lyf
 * @since 2024-06-06
 */
public interface IFrameTenantService extends IService<Tenant> {

    Result getAllTenantDTOs();


    List<Tenant> searchTenant(String name, Integer state);

    boolean initialize(TenantAdminDTO tenant);
}
