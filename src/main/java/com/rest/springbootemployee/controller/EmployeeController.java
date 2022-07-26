package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.exception.EmployeeNotFindException;
import com.rest.springbootemployee.mapper.EmployRepository;
import com.rest.springbootemployee.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployRepository repository;

    @GetMapping(params = {"gender"})
    public List<Employee> queryGender(@RequestParam("gender") String gender) {
        return repository.queryByGender(gender);
    }

    @GetMapping("/{id}")
    public Employee queryEmployById(@PathVariable String id)   {
        return repository.queryEmployeeById(id);
    }

    @GetMapping
    public List<Employee> queryAllEmployees() {
        return repository.getAllEmployees();
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> findByPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        return repository.findByPage(page, pageSize);
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String addAnEmployee(@RequestBody Employee employee) {
        Employee employeeFromDb = repository.queryEmployeeById(employee.getId());
        if (employeeFromDb == null) {
            repository.insert(employee);
            return Constant.ADD_EMPLOYEE_SUCCESS;
        }
        return Constant.EMPLOYEE_IS_EXIST;
    }

    @PutMapping
    public String updateAnEmployee(@RequestBody Employee employee) {
        Employee employeeFromDb = repository.queryEmployeeById(employee.getId());
        if (employeeFromDb != null) {
            return Constant.EMPLOYEE_IS_EXIST;
        }
        employeeFromDb.setSalary(employee.getSalary());
        return Constant.UPDATE_EMPLOYEE_SUCCESS;

    }


}
