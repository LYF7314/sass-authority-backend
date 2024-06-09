package edu.hitwh.controller;

import edu.hitwh.entity.Function;
import edu.hitwh.service.IFrameFunctionService;
import edu.hitwh.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/function")
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
        return frameFunctionService.createFunction(function);
    }

    /**
     * 获取所有功能
     * @return 功能树
     */
    @GetMapping("/all")
    public Result getAllFunctions() {
        return frameFunctionService.getAllFunctions();
    }

    /**
     * 更新功能
     * @param function 更新的功能实体
     * @return 成功信息
     */
    @PutMapping("/update")
    public Result updateFunction(@RequestBody Function function) {
        return frameFunctionService.updateFunction(function);
    }

    /**
     * 删除功能
     * @param id 功能id
     * @return 成功信息
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteFunction(@PathVariable Long id) {
        return frameFunctionService.deleteFunction(id);
    }
}
