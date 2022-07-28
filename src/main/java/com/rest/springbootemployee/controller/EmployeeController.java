package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.EmployeeRepository;
import com.rest.springbootemployee.services.EmployeeService;
import com.rest.springbootemployee.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //companyId:1552582897884463104
    @GetMapping(params = {"gender"})
    public List<Employee> queryGender(@RequestParam("gender") String gender) {
        return employeeService.queryEmployeeByGender(gender);
    }

    @GetMapping("/{id}")
    public Employee queryEmployeeById(@PathVariable String id)   {
        return employeeService.queryEmployeeById(id);
    }

    @GetMapping
    public List<Employee> queryAllEmployees() {
        return employeeService.findALl();
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> findByPage(@RequestParam("page") int page,
                                     @RequestParam("pageSize") int pageSize) {
        return employeeService.findEmployeeByPage(page, pageSize);
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Employee addAnEmployee(@RequestBody Employee employee) {
        employeeService.insertEmployee(employee);
        return employee;
    }

    @PutMapping("/{id}")
    public Employee updateAnEmployee(@PathVariable("id") String employeeId,
                                   @RequestBody Employee employee) {
        return employeeService.update(employeeId,employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Employee deleteAnEmployee(@PathVariable String id) {
        return employeeService.removeEmployee(id);
    }

}
