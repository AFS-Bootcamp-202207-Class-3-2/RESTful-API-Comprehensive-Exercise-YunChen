package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.EmployeeDao;
import com.rest.springbootemployee.services.HelloService;
import com.rest.springbootemployee.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping(path = "/{userName}")
    public String getAll(@PathVariable String userName) {

        return "Hello:" + userName;
    }

    @Autowired
    private HelloService helloService;

    @PutMapping("/add")
    public Employee insert(@RequestBody Employee employee) {
        return helloService.insertEmployee(employee);
    }
    @GetMapping("/r")
    public R test1(){
        Employee employee = new Employee();
        employee.setName("YunChen2");
        employee.setGender("Man");
        employee.setSalary(1);
        return R.SUCCESS().data("name", "YunChen").data("employee",employee);
    }

    @Autowired
    private EmployeeDao employeeDao;

    @GetMapping(value = "/pages", params = {"page", "pageSize"})

    public R test2(@RequestParam("page")int page,@RequestParam("pageSize")int pageSize) {
        Page<Employee> pageData = employeeDao.findAll(
                PageRequest.of(page, pageSize,  Sort.by(Sort.Direction.DESC,"createTime")));
        return R.SUCCESS("pageTest").data("data", pageData);
    }
}
