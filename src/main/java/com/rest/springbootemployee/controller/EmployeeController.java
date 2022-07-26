package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.enities.Employee;
import com.rest.springbootemployee.mapper.EmployRepository;
import com.rest.springbootemployee.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployRepository employRepository;

    @GetMapping(params = {"gender"})
    public List<Employee> queryGender(@RequestParam("gender") String gender) {
        return employRepository.queryByGender(gender);
    }

    @GetMapping("/{id}")
    public Employee queryEmployById(@PathVariable String id)   {
        return employRepository.queryEmployeeById(id);
    }

    @GetMapping
    public List<Employee> queryAllEmployees() {
        return employRepository.getAllEmployees();
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> findByPage(@RequestParam("page") int page,
                                     @RequestParam("pageSize") int pageSize) {
        return employRepository.findByPage(page, pageSize);
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String addAnEmployee(@RequestBody Employee employee) {
        Employee employeeFromDb = employRepository.queryEmployeeById(employee.getId());
        if (employeeFromDb == null) {
            employRepository.insert(employee);
            return Constant.ADD_EMPLOYEE_SUCCESS;
        }
        return Constant.EMPLOYEE_IS_EXIST;
    }

    @PutMapping
    public String updateAnEmployee(@RequestBody Employee employee) {
        return employRepository.updateAnEmployee(employee)?
                Constant.UPDATE_EMPLOYEE_SUCCESS:
                Constant.EMPLOYEE_IS_NOT_EXIST;
    }

    @DeleteMapping("/{id}")
    public String deleteAnEmployee(@PathVariable String id) {
        return employRepository.deleteEmployee(id) ?
                Constant.DELETE_EMPLOYEE_SUCCESS :
                Constant.EMPLOYEE_IS_NOT_EXIST;
    }

}
