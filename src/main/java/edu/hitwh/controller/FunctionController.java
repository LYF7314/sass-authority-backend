package edu.hitwh.controller;

import edu.hitwh.entity.Function;
import edu.hitwh.service.IFrameFunctionService;
import edu.hitwh.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/function")
public class FunctionController {

    @Resource
    private IFrameFunctionService frameFunctionService;

    @PostMapping("/add")
    public Result createFunction(@RequestBody Function function) {
        return frameFunctionService.createFunction(function);
    }

    @GetMapping("/all")
    public Result getAllFunctions() {
        return frameFunctionService.getAllFunctions();
    }

    @PutMapping("/update")
    public Result updateFunction(@RequestBody Function function) {
        return frameFunctionService.updateFunction(function);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteFunction(@PathVariable Long id) {
        return frameFunctionService.deleteFunction(id);
    }
}
