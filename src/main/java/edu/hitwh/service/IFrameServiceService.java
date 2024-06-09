package edu.hitwh.service;

import edu.hitwh.entity.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.hitwh.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lyf
 * @since 2024-06-06
 */
public interface IFrameServiceService extends IService<Service> {

    Result getServiceRefers();

}
