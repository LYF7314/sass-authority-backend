package edu.hitwh.service.impl;

import edu.hitwh.dto.FunctionNode;
import edu.hitwh.service.IFrameTenantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FrameTenantServiceImplTest {
    @Resource
    private IFrameTenantService frameTenantService;
    @Test
    void getTenantFunctions() {
        List<FunctionNode> functionNodes = frameTenantService.getTenantFunctions(Long.parseLong("4"));
    }

    @Test
    void completeTenantFunction() {

    }
}