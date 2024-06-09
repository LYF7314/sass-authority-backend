package edu.hitwh.controller;

import edu.hitwh.service.IFrameFunctionService;
import edu.hitwh.service.IFrameServiceService;
import edu.hitwh.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Resource
    private IFrameServiceService frameServiceService;

    /*
     * @Author Liyifan
     * @Description get service reference
     * @Date 17:20 2024/6/8
     * @Param []
     * @return edu.hitwh.utils.Result
     **/
    @GetMapping("/refer")
    public Result getServiceRefers() {
        return frameServiceService.getServiceRefers();
    }
}
