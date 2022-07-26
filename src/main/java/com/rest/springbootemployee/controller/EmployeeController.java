package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.exception.EmployeeNotFindException;
import com.rest.springbootemployee.mapper.EmployRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Employee queryEmployById(@PathVariable String id) throws EmployeeNotFindException {

        return repository.queryEmployeeById(id);
    }

    @GetMapping
    public List<Employee> queryAllEmployees() {
        return repository.getAllEmployees();
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> findByPage(@RequestParam("page")int page,@RequestParam("pageSize")int pageSize) {
        return repository.findByPage(page, pageSize);
    }



}
