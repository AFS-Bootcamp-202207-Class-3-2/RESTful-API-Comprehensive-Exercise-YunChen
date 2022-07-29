package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.Dto.EmployeePageResponse;
import com.rest.springbootemployee.Dto.EmployeeResponse;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.EmployeeRepository;
import com.rest.springbootemployee.services.EmployeeService;
import com.rest.springbootemployee.utils.Constant;
import com.rest.springbootemployee.utils.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //companyId:1552582897884463104
    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> queryGender(@RequestParam("gender") String gender) {
        List<EmployeeResponse> returnList = new ArrayList<>();
        List<Employee> employees = employeeService.queryEmployeeByGender(gender);
        employees.forEach(employee -> returnList.add(EmployeeMapper.toResponse(employee)));
        return returnList;
    }

    @GetMapping("/{id}")
    public EmployeeResponse queryEmployeeById(@PathVariable String id)   {
        return EmployeeMapper.toResponse(employeeService.queryEmployeeById(id));
    }

    @GetMapping
    public List<EmployeeResponse> queryAllEmployees() {
        List<Employee> employees = employeeService.findALl();
        List<EmployeeResponse> returnList = new ArrayList<>();
        for (int idx = 0; idx < employees.size(); idx++) {
            returnList.add(EmployeeMapper.toResponse(employees.get(idx)));
        }
        return returnList;
    }

    @GetMapping(params = {"page", "pageSize"})
    public EmployeePageResponse findByPage(@RequestParam("page") int page,
                                           @RequestParam("pageSize") int pageSize) {
        Page<Employee> employeeByPage = employeeService.findEmployeeByPage(page, pageSize);
        return EmployeeMapper.toResponse(employeeByPage);
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Employee addAnEmployee(@RequestBody Employee employee) {
        return employeeService.insertEmployee(employee);
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
