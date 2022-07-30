package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.Dto.EmployeeRequest;
import com.rest.springbootemployee.Dto.EmployeeResponse;
import com.rest.springbootemployee.Dto.PageResponse;
import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.services.EmployeeService;
import com.rest.springbootemployee.utils.MapperDtoUtil;
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
    public List<EmployeeResponse> queryGender(@RequestParam("gender") String gender) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        return MapperDtoUtil.<EmployeeResponse, Employee>toResponse(employeeService.queryEmployeeByGender(gender), EmployeeResponse.class);
    }

    @GetMapping("/{id}")
    public EmployeeResponse queryEmployeeById(@PathVariable String id) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        return MapperDtoUtil.<EmployeeResponse, Employee>toResponse(employeeService.queryEmployeeById(id), EmployeeResponse.class);
    }

    @GetMapping
    public List<EmployeeResponse> queryAllEmployees() throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        return MapperDtoUtil.<EmployeeResponse, Employee>toResponse(employeeService.findALl(), EmployeeResponse.class);
    }

    @GetMapping(params = {"page", "pageSize"})
    public PageResponse<Employee> findByPage(@RequestParam("page") int page,
                                   @RequestParam("pageSize") int pageSize) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        return MapperDtoUtil.<Employee>toResponse(employeeService.findEmployeeByPage(page, pageSize), Employee.class);
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmployeeResponse addAnEmployee(@RequestBody EmployeeRequest employee) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return MapperDtoUtil.<EmployeeResponse, Employee>toResponse(employeeService.insertEmployee(
                MapperDtoUtil.toRequest(employee, Employee.class)
        ), EmployeeResponse.class);
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateAnEmployee(@PathVariable("id") String employeeId,
                                   @RequestBody EmployeeRequest employee) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return MapperDtoUtil.<EmployeeResponse, Employee>toResponse(employeeService.update(employeeId,
                MapperDtoUtil.toRequest(employee, Employee.class)),
                EmployeeResponse.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAnEmployee(@PathVariable String id) {
        employeeService.removeEmployee(id);
    }

}
