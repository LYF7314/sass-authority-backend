package edu.hitwh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hitwh.entity.UserFunctionView;
import edu.hitwh.mapper.FrameUserFunctionViewMapper;
import edu.hitwh.service.IFrameUserFunctionViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FrameUserFunctionViewServiceImpl extends ServiceImpl<FrameUserFunctionViewMapper, UserFunctionView> implements IFrameUserFunctionViewService {

}
