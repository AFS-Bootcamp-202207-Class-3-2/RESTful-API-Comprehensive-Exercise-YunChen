package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.Dto.EmployeePageResponse;
import com.rest.springbootemployee.Dto.EmployeeRequest;
import com.rest.springbootemployee.Dto.EmployeeResponse;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.services.EmployeeService;
import com.rest.springbootemployee.utils.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public EmployeeResponse addAnEmployee(@RequestBody EmployeeRequest employee) {
        return EmployeeMapper.toResponse(employeeService.insertEmployee(EmployeeMapper.toRequest(employee)));
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateAnEmployee(@PathVariable("id") String employeeId,
                                   @RequestBody EmployeeRequest employee) {
        return EmployeeMapper.toResponse(employeeService.update(employeeId,EmployeeMapper.toRequest(employee)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAnEmployee(@PathVariable String id) {
        employeeService.removeEmployee(id);
    }

}
