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
        return EmployeeMapper.toResponse(employeeService.queryEmployeeByGender(gender));
    }

    @GetMapping("/{id}")
    public EmployeeResponse queryEmployeeById(@PathVariable String id)   {
        return EmployeeMapper.toResponse(employeeService.queryEmployeeById(id));
    }

    @GetMapping
    public List<EmployeeResponse> queryAllEmployees() {
        return EmployeeMapper.toResponse(employeeService.findALl());
    }

    @GetMapping(params = {"page", "pageSize"})
    public EmployeePageResponse findByPage(@RequestParam("page") int page,
                                           @RequestParam("pageSize") int pageSize) {
        return EmployeeMapper.toResponse(employeeService.findEmployeeByPage(page, pageSize));
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
