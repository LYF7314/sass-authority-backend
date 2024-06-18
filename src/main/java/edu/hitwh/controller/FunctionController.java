package edu.hitwh.controller;

import edu.hitwh.config.RedisSessionInterceptor;
import edu.hitwh.entity.Function;
import edu.hitwh.service.IFrameFunctionService;
import edu.hitwh.utils.RedisConstants;
import edu.hitwh.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
@Slf4j
@RestController
@RequestMapping("/function")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FunctionController {
    @Resource
    private IFrameFunctionService frameFunctionService;

    /**
     * 添加功能
     * @param function 功能实体或功能目录
     * @return 成功信息
     */
    @PostMapping("/add")
    public Result createFunction(@RequestBody Function function) {
        log.info("create function: {}",function);
        return frameFunctionService.createFunction(function);
    }

    /**
     * 获取所有功能
     * @return 功能树
     */
    @GetMapping("/all")
    public Result getAllFunctions(HttpSession session) {
        log.info("get all functions session: {}",session.getAttribute(RedisConstants.LOGIN_INFO_KEY));
        return frameFunctionService.getAllFunctions();
    }

    /**
     * 更新功能
     * @param function 更新的功能实体
     * @return 成功信息
     */
    @PutMapping("/update")
    public Result updateFunction(@RequestBody Function function) {
        log.info("update function: {}",function);
        return frameFunctionService.updateFunction(function);
    }

    /**
     * 删除功能
     * @param id 功能id
     * @return 成功信息
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteFunction(@PathVariable Long id) {
        log.info("delete function: {}",id);
        return frameFunctionService.deleteFunction(id);
    }
}
