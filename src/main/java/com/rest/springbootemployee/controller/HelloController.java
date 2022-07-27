package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping(path = "/{userName}")
    public String getAll(@PathVariable String userName) {

        return "Hello:" + userName;
    }

    @GetMapping("/r")
    public R test(){
        Employee employee = new Employee();
        employee.setName("YunChen2");
        employee.setGender("Man");
        employee.setSalary(1);
        return R.SUCCESS().data("name", "YunChen").data("employee",employee);
    }
}
