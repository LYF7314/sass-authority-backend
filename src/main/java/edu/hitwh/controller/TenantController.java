package edu.hitwh.controller;

import edu.hitwh.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TenantController {

    @PostMapping("/tenant/add")
    public Result addTenant() {
        return Result.fail("Not implemented");
    }

    @GetMapping("/tenant/all")
    public Result getAllTenants() {
        return Result.fail("Not implemented");
    }

    @GetMapping("/tenant")
    public Result getTenant() {
        return Result.fail("Not implemented");
    }


}
